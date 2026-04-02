package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit


data class CheckInSettings(
    val intervalDays: Int,
    val missedThreshold: Int,
    val reminderDaysBefore: Int,
    val emailEnabled: Boolean,
    val smsEnabled: Boolean,
    val pushEnabled: Boolean
)