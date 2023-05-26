package com.bcgg.android.activity.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.bcgg.android.activity.PlanManageActivity

class PlanManageActivityContract : ActivityResultContract<Unit, Unit>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, PlanManageActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }
}