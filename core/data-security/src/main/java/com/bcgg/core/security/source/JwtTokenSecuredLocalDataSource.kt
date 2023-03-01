package com.bcgg.core.security.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bcgg.core.security.model.JwtToken
import com.bcgg.core.security.util.DataStoreUtil.secureEdit
import com.bcgg.core.security.util.DataStoreUtil.secureMap
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JwtTokenSecuredLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    fun getJwtToken(): Flow<JwtToken?> = context.dataStore.data.secureMap {
        it[TOKEN_PREFS_KEY].orEmpty()
    }

    suspend fun saveJwtToken(token: JwtToken) {
        context.dataStore.secureEdit(token) { prefs, encrypedValue ->
            prefs[TOKEN_PREFS_KEY] = encrypedValue
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "tokens"
        private val TOKEN_PREFS_KEY = stringPreferencesKey("token")
    }
}
