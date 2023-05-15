package com.bcgg.core.ui.navigation

open class Navigation(
    val id: String
) {
    override fun toString(): String {
        return id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}