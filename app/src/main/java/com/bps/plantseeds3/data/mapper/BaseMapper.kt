package com.bps.plantseeds3.data.mapper

import android.util.Log

abstract class BaseMapper<Entity, Domain> {
    protected abstract val TAG: String

    fun toDomain(entity: Entity): Domain {
        Log.d(TAG, "Konverterar Entity till Domain: $entity")
        return try {
            entity.convertToDomain()
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid konvertering till Domain", e)
            throw e
        }
    }

    fun toEntity(domain: Domain): Entity {
        Log.d(TAG, "Konverterar Domain till Entity: $domain")
        return try {
            domain.convertToEntity()
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid konvertering till Entity", e)
            throw e
        }
    }

    protected abstract fun Entity.convertToDomain(): Domain
    protected abstract fun Domain.convertToEntity(): Entity

    protected fun <T> safeConvert(
        value: T?,
        converter: (T) -> String,
        defaultValue: String = ""
    ): String {
        return try {
            value?.let { converter(it) } ?: defaultValue
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid konvertering av värde: $value", e)
            defaultValue
        }
    }

    protected fun <T> safeConvertEnum(
        value: String?,
        converter: (String) -> T,
        defaultValue: T
    ): T {
        return try {
            value?.let { converter(it) } ?: defaultValue
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid konvertering av enum: $value", e)
            defaultValue
        }
    }
} 