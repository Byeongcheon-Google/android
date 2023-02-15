package com.bcgg.core.domain.model

import java.time.LocalDate

data class Schedule(
    val name: String,
    val destinations: List<Destination>,
    val status: Status
) {
    enum class Status {
        BeforeRun, Running, ResultCreated
    }

    fun getFilteredDestinations(date: LocalDate) = destinations.filter { it.comeTime.toLocalDate() == date }
}

fun newSchedule() = Schedule(
    name = "Schedule",
    destinations = listOf(),
    status = Schedule.Status.BeforeRun
)
