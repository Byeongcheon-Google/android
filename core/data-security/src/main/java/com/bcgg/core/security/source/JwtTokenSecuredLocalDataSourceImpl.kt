package com.bcgg.core.security.source

import android.content.Context
import com.bcgg.core.security.model.JwtToken
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JwtTokenSecuredLocalDataSourceImpl(
    @ApplicationContext private val context: Context
) : JwtTokenSecuredLocalDataSource {

    init {
        Hawk.init(context).build()
    }

    override fun getJwtToken(): Flow<JwtToken> = flow {
        emit(Hawk.get(TOKEN_PREFS_KEY))
    }

    override suspend fun saveJwtToken(token: JwtToken) {
        Hawk.put(TOKEN_PREFS_KEY, token)
    }

    companion object {
        private val TOKEN_PREFS_KEY = "token"
    }
}
