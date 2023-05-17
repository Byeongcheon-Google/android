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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Navigation

        if (id != other.id) return false

        return true
    }
}