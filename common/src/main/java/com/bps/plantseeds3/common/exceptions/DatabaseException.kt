package com.bps.plantseeds3.common.exceptions

sealed class DatabaseException(message: String) : Exception(message) {
    class EntityNotFoundException(entityType: String, id: Any) : 
        DatabaseException("$entityType med id $id hittades inte")
    
    class InsertionFailedException(entityType: String, cause: Throwable? = null) : 
        DatabaseException("Kunde inte lägga till $entityType") {
        init {
            cause?.let { initCause(it) }
        }
    }
    
    class UpdateFailedException(entityType: String, id: Any, cause: Throwable? = null) : 
        DatabaseException("Kunde inte uppdatera $entityType med id $id") {
        init {
            cause?.let { initCause(it) }
        }
    }
    
    class DeletionFailedException(entityType: String, id: Any, cause: Throwable? = null) : 
        DatabaseException("Kunde inte ta bort $entityType med id $id") {
        init {
            cause?.let { initCause(it) }
        }
    }
    
    class MigrationFailedException(fromVersion: Int, toVersion: Int, cause: Throwable? = null) : 
        DatabaseException("Kunde inte migrera databasen från version $fromVersion till $toVersion") {
        init {
            cause?.let { initCause(it) }
        }
    }
    
    class QueryFailedException(operation: String, cause: Throwable? = null) : 
        DatabaseException("Databasoperationen '$operation' misslyckades") {
        init {
            cause?.let { initCause(it) }
        }
    }
} 