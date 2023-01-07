package com.bitta.app.model

data class Dispenser(
    val id: Int,
    val address: String,
    val locationDescription: String,
)

data class DispenserWithStatus(val dispenser: Dispenser, val workingStatus: WorkingStatus)

enum class WorkingStatus {
    OK,
    WITH_REPORTS,
    NOT_WORKING,
}