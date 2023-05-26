package com.bcgg.android.activity.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.bcgg.android.activity.SignupActivity

class SignupContract : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, SignupActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(ID)
    }

    companion object {
        const val ID = "USER_ID"
    }
}