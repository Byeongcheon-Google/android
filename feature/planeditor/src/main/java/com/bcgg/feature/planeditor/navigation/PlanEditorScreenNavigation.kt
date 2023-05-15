package com.bcgg.feature.planeditor.navigation

import com.bcgg.core.ui.navigation.Navigation

object PlanEditorScreenNavigation : Navigation("PLAN_EDITOR") {
    fun withPlanId(planId: Int) = "$id/$planId"
}