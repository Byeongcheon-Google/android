package com.bcgg.core.security.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object DataStoreUtil {
    const val securityKeyAlias = "data-store"
    const val bytesToStringSeparator = "|"

    val json = Json { encodeDefaults = true }

    /**
     * serializes data type into string
     * performs encryption
     * stores encrypted data in DataStore
     */
    suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue =
                SecurityUtil.encryptData(securityKeyAlias, Json.encodeToString(value))
            editStore.invoke(it, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }

    /**
     * fetches encrypted data from DataStore
     * performs decryption
     * deserializes data into respective data type
     */
    inline fun <reified T> Flow<Preferences>.secureMap(
        crossinline fetchValue: (value: Preferences) -> String
    ): Flow<T> {
        return map {
            val decryptedValue = SecurityUtil.decryptData(
                securityKeyAlias,
                fetchValue(it).split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )

            json.decodeFromString(decryptedValue)
        }
    }
}
