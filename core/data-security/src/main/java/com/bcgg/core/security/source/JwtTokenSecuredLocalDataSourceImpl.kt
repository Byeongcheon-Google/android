package com.bcgg.core.security.source

import android.content.Context
import com.bcgg.core.security.model.JwtToken
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class JwtTokenSecuredLocalDataSourceImpl(
    @ApplicationContext private val context: Context
) : JwtTokenSecuredLocalDataSource {

    init {
        Hawk.init(context).build()
    }

    override fun saveAccessToken(accessToken: String) {
        Hawk.put(TOKEN_PREFS_KEY, accessToken)
    }

    override fun getAccessToken(): String? = Hawk.get<String>(TOKEN_PREFS_KEY)
    override fun removeAccessToken() {
        Hawk.delete(TOKEN_PREFS_KEY)
    }

    companion object {
        private const val TOKEN_PREFS_KEY = "token"
    }
}
