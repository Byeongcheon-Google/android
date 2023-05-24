package com.bcgg.feature.planeditor.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class PlanEditorContract : ActivityResultContract<Int?, Unit>() {
    override fun createIntent(context: Context, input: Int?): Intent {
        return Intent(context, PlanEditorActivity::class.java).apply {
            putExtra(PLAN_ID, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {

    }

    companion object {
        const val PLAN_ID = "PLAN_ID"
    }
}