# API-dokumentation - Common-modulen

## Översikt
Detta dokument beskriver API:et för common-modulen, inklusive dess komponenter, metoder och användning.

## UI-komponenter

### CommonButton
```kotlin
@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.Primary
)
```

### CommonTextField
```kotlin
@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
)
```

### CommonCard
```kotlin
@Composable
fun CommonCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
```

### CommonDialog
```kotlin
@Composable
fun CommonDialog(
    title: String,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null
)
```

## Verktyg

### DateUtils
```kotlin
object DateUtils {
    fun formatDate(date: LocalDate): String
    fun parseDate(dateString: String): LocalDate?
    fun isValidDate(dateString: String): Boolean
    fun getCurrentDate(): LocalDate
}
```

### StringUtils
```kotlin
object StringUtils {
    fun capitalize(text: String): String
    fun truncate(text: String, maxLength: Int): String
    fun isValidEmail(email: String): Boolean
    fun removeSpecialCharacters(text: String): String
}
```

### ImageUtils
```kotlin
object ImageUtils {
    suspend fun loadImage(url: String): Bitmap?
    fun compressImage(bitmap: Bitmap, quality: Int): ByteArray
    fun resizeImage(bitmap: Bitmap, maxSize: Int): Bitmap
}
```

### ValidationUtils
```kotlin
object ValidationUtils {
    fun validateEmail(email: String): ValidationResult
    fun validatePassword(password: String): ValidationResult
    fun validateRequired(value: String): ValidationResult
}
```

## Datamodeller

### Result
```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
```

### Error
```kotlin
sealed class AppError : Exception() {
    data class NetworkError(override val message: String) : AppError()
    data class ValidationError(override val message: String) : AppError()
    data class DatabaseError(override val message: String) : AppError()
    data class UnknownError(override val message: String) : AppError()
}
```

### Resource
```kotlin
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()
}
```

## Konstanter
```kotlin
object Constants {
    const val MAX_NAME_LENGTH = 50
    const val MIN_PASSWORD_LENGTH = 8
    const val MAX_DESCRIPTION_LENGTH = 500
    const val IMAGE_COMPRESSION_QUALITY = 80
    const val MAX_IMAGE_SIZE = 1024
}
```

## Användning

### UI-komponenter
```kotlin
CommonButton(
    text = "Spara",
    onClick = { /* hantera klick */ }
)

CommonTextField(
    value = text,
    onValueChange = { text = it },
    label = "Namn"
)
```

### Verktyg
```kotlin
val formattedDate = DateUtils.formatDate(LocalDate.now())
val isValid = StringUtils.isValidEmail("test@example.com")
```

### Datamodeller
```kotlin
when (result) {
    is Result.Success -> handleSuccess(result.data)
    is Result.Error -> handleError(result.exception)
    is Result.Loading -> showLoading()
}
```

## Beroenden
- AndroidX Core
- AndroidX Compose
- Kotlin Standard Library
- Hilt för DI 