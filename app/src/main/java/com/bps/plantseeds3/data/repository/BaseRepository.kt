package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.mapper.BaseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class BaseRepository<Entity, Domain> {
    protected abstract val TAG: String
    protected abstract val dao: Any // BaseDao<Entity>
    protected abstract val mapper: BaseMapper<Entity, Domain>

    protected suspend fun insert(item: Domain, insertFunction: suspend (Entity) -> Unit) {
        Log.d(TAG, "Sparar i databasen: $item")
        val entity = mapper.toEntity(item)
        insertFunction(entity)
        Log.d(TAG, "Sparat i databasen: $item")
    }

    protected suspend fun update(item: Domain, updateFunction: suspend (Entity) -> Unit) {
        Log.d(TAG, "Uppdaterar i databasen: $item")
        updateFunction(mapper.toEntity(item))
    }

    protected suspend fun delete(item: Domain, deleteFunction: suspend (Entity) -> Unit) {
        Log.d(TAG, "Tar bort från databasen: $item")
        deleteFunction(mapper.toEntity(item))
    }

    protected suspend fun deleteAll(deleteAllFunction: suspend () -> Unit) {
        Log.d(TAG, "Tar bort allt från databasen")
        deleteAllFunction()
    }

    protected suspend fun getById(id: String, getByIdFunction: suspend (String) -> Entity?): Domain? {
        Log.d(TAG, "Hämtar med ID: $id")
        val item = getByIdFunction(id)?.let { mapper.toDomain(it) }
        Log.d(TAG, "Hämtat: ${item ?: "null"}")
        return item
    }

    protected fun getAll(
        getAllFunction: () -> Flow<List<Entity>>,
        transform: (Entity) -> Domain
    ): Flow<List<Domain>> = flow {
        try {
            Log.d(TAG, "Hämtar alla från databasen")
            getAllFunction().collect { items ->
                Log.d(TAG, "Hämtade ${items.size} från databasen")
                items.forEach { item ->
                    Log.d(TAG, "Item: $item")
                }
                val domainItems = items.map { item ->
                    Log.d(TAG, "Konverterar till domain: $item")
                    transform(item)
                }
                Log.d(TAG, "Konverterade ${domainItems.size} till domain-modeller")
                emit(domainItems)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid hämtning", e)
            emit(emptyList())
        }
    }

    protected fun search(
        query: String,
        searchFunction: (String) -> Flow<List<Entity>>,
        transform: (Entity) -> Domain
    ): Flow<List<Domain>> {
        Log.d(TAG, "Söker med query: $query")
        return searchFunction(query).map { items ->
            Log.d(TAG, "Hittade ${items.size} som matchar sökningen")
            items.map { transform(it) }
        }
    }
} 