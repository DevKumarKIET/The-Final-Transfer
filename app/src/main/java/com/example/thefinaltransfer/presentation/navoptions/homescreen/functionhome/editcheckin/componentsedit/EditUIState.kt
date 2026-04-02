package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit


/**
 * UI State for the Edit Check-In screen.
 * Single source of truth consumed by the ViewModel and Screen.
 */
data class EditCheckInUiState(

    //Interval Selection
    val selectedChip: String = "Weekly",
    val customDuration: String = "",
    val customUnit: String = "Days",

    //Missed Check-In Threshold
    val missedCheckInThreshold: Int = 3,

    //Reminder Settings
    val reminderDaysBefore: String = "2",
    val emailEnabled: Boolean = true,
    val smsEnabled: Boolean = true,
    val pushEnabled: Boolean = true,

    //Validation
    val customDurationError: String? = null,
    val reminderDaysError: String? = null,
    val isSaveEnabled: Boolean = true,

    //Feedback
    val showSuccessSnackbar: Boolean = false,
    val errorMessage: String? = null
)