# API-integration

## Översikt
PlantSeeds3 integrerar med flera externa API:er för att tillhandahålla omfattande funktionalitet för trädgårdsplanering och växtskötsel.

## Firebase-integration

### 1. Konfiguration
```kotlin
class FirebaseModule @Module @InstallIn(SingletonComponent::class) {
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    
    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    
    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}
```

### 2. Datamodeller
```kotlin
data class PlantDto(
    val id: String = "",
    val seedId: String = "",
    val gardenId: String = "",
    val name: String = "",
    val status: String = "",
    val plantedDate: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)

data class GardenDto(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val location: String? = null,
    val size: String = "",
    val type: String = "",
    val plants: List<String> = emptyList(),
    val updatedAt: Timestamp = Timestamp.now()
)
```

### 3. Repository-implementation
```kotlin
class PlantFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : PlantRepository {
    override suspend fun getPlants(): Result<List<Plant>> {
        return try {
            val snapshot = firestore.collection("plants")
                .get()
                .await()
            
            Result.success(
                snapshot.toObjects<PlantDto>()
                    .map { it.toPlant() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun addPlant(plant: Plant): Result<Unit> {
        return try {
            firestore.collection("plants")
                .document(plant.id)
                .set(plant.toDto())
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## Väderdata API

### 1. Konfiguration
```kotlin
class WeatherApiModule @Module @InstallIn(SingletonComponent::class) {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}
```

### 2. API-gränssnitt
```kotlin
interface WeatherApi {
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse
    
    @GET("current")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): CurrentWeather
}
```

### 3. Datamodeller
```kotlin
data class WeatherResponse(
    val list: List<WeatherData>,
    val city: City
)

data class WeatherData(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class CurrentWeather(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)
```

## Växtdatabas API

### 1. Konfiguration
```kotlin
class PlantDatabaseApiModule @Module @InstallIn(SingletonComponent::class) {
    @Provides
    @Singleton
    fun providePlantDatabaseApi(): PlantDatabaseApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.PLANT_DATABASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlantDatabaseApi::class.java)
    }
}
```

### 2. API-gränssnitt
```kotlin
interface PlantDatabaseApi {
    @GET("plants/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("api_key") apiKey: String
    ): PlantSearchResponse
    
    @GET("plants/{id}")
    suspend fun getPlantDetails(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): PlantDetails
}
```

### 3. Datamodeller
```kotlin
data class PlantSearchResponse(
    val data: List<PlantInfo>
)

data class PlantInfo(
    val id: String,
    val commonName: String,
    val scientificName: String,
    val imageUrl: String?
)

data class PlantDetails(
    val id: String,
    val commonName: String,
    val scientificName: String,
    val description: String,
    val careInstructions: CareInstructions,
    val growingSeason: GrowingSeason
)
```

## API-hantering

### 1. Felhantering
```kotlin
sealed class ApiException : Exception() {
    class NetworkError(override val cause: Throwable) : ApiException()
    class ServerError(val code: Int, val message: String) : ApiException()
    class AuthenticationError(val message: String) : ApiException()
    class RateLimitExceeded(val retryAfter: Long) : ApiException()
}

class ApiErrorHandler {
    fun handleError(error: Throwable): ApiException {
        return when (error) {
            is IOException -> ApiException.NetworkError(error)
            is HttpException -> when (error.code()) {
                401 -> ApiException.AuthenticationError("Invalid API key")
                429 -> ApiException.RateLimitExceeded(
                    error.response()?.header("Retry-After")?.toLong() ?: 60
                )
                else -> ApiException.ServerError(error.code(), error.message())
            }
            else -> ApiException.NetworkError(error)
        }
    }
}
```

### 2. Retry-logik
```kotlin
class RetryInterceptor @Inject constructor(
    private val apiErrorHandler: ApiErrorHandler
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var retryCount = 0
        var lastException: Exception? = null
        
        while (retryCount < MAX_RETRIES) {
            try {
                return chain.proceed(chain.request())
            } catch (e: Exception) {
                lastException = e
                val apiException = apiErrorHandler.handleError(e)
                
                when (apiException) {
                    is ApiException.RateLimitExceeded -> {
                        Thread.sleep(apiException.retryAfter * 1000)
                        retryCount++
                    }
                    is ApiException.NetworkError -> {
                        if (retryCount < MAX_RETRIES) {
                            Thread.sleep(RETRY_DELAY)
                            retryCount++
                        } else {
                            throw apiException
                        }
                    }
                    else -> throw apiException
                }
            }
        }
        
        throw lastException ?: ApiException.NetworkError(IOException("Max retries exceeded"))
    }
    
    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY = 1000L
    }
}
```

### 3. Cachning
```kotlin
class ApiCache @Inject constructor(
    private val cache: Cache
) {
    fun <T> get(key: String): T? {
        return cache.get(key) as? T
    }
    
    fun <T> put(key: String, value: T, ttl: Long = DEFAULT_TTL) {
        cache.put(key, value, ttl)
    }
    
    fun invalidate(key: String) {
        cache.remove(key)
    }
    
    companion object {
        private const val DEFAULT_TTL = 3600L // 1 timme
    }
}
```

## Best Practices

### 1. API-integration
- Använd dependency injection
- Implementera felhantering
- Hantera API-nycklar säkert
- Följ API-begränsningar

### 2. Nätverkshantering
- Implementera retry-logik
- Använd effektiv cachning
- Hantera offline-läge
- Optimera nätverksanrop

### 3. Datasynkronisering
- Synka data regelbundet
- Hantera konflikter
- Implementera offline-stöd
- Validera data

### 4. Säkerhet
- Skydda API-nycklar
- Implementera autentisering
- Hantera känslig data
- Följ säkerhetsriktlinjer

### 5. Prestanda
- Optimera API-anrop
- Implementera paginering
- Cacha svar
- Hantera stora datamängder 