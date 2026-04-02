package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.componentsedit.EditCheckInUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val chipToDays = mapOf(
    "Daily"    to 1,
    "3 Days"   to 3,
    "Weekly"   to 7,
    "2 Weeks"  to 14,
    "Monthly"  to 30,
    "3 Months" to 90,
    "Custom"   to -1   // -1 signals user-defined
)

class EditCheckInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EditCheckInUiState())
    val uiState: StateFlow<EditCheckInUiState> = _uiState.asStateFlow()


    // Called when user taps a quick-select chip
    fun onIntervalChipSelected(chip: String) {
        _uiState.update { it.copy(selectedChip = chip, customDurationError = null) }
        validateAndUpdateSaveState()
    }
    fun onCustomDurationChanged(value: String) {
        _uiState.update { it.copy(customDuration = value, customDurationError = null) }
        validateAndUpdateSaveState()
    }

    /** Called when user picks a unit from the dropdown (Days / Weeks / Months) */
    fun onCustomUnitChanged(unit: String) {
        _uiState.update { it.copy(customUnit = unit) }
        validateAndUpdateSaveState()
    }

    // Missed check-in threshold stepper
    fun onMissedCheckInCountChanged(delta: Int) {
        _uiState.update { state ->
            val newVal = (state.missedCheckInThreshold + delta).coerceIn(1, 10)
            state.copy(missedCheckInThreshold = newVal)
        }
    }

    // Reminder settings
    fun onReminderDaysChanged(days: String) {
        _uiState.update { it.copy(reminderDaysBefore = days, reminderDaysError = null) }
        validateAndUpdateSaveState()
    }

    fun onEmailToggle(enabled: Boolean) =
        _uiState.update { it.copy(emailEnabled = enabled) }

    fun onSmsToggle(enabled: Boolean) =
        _uiState.update { it.copy(smsEnabled = enabled) }

    fun onPushToggle(enabled: Boolean) =
        _uiState.update { it.copy(pushEnabled = enabled) }

    // Save
    fun onSaveChanges() {
        if (!validate()) return

        viewModelScope.launch {
            // TODO: Inject & call repository use-case here
            _uiState.update { it.copy(showSuccessSnackbar = true) }
            delay(3000)
            _uiState.update { it.copy(showSuccessSnackbar = false) }
        }
    }

    fun onSnackbarDismissed() {
        _uiState.update { it.copy(showSuccessSnackbar = false) }
    }

    // Private helpers
    private fun validate(): Boolean {
        val state = _uiState.value
        var isValid = true

        // Validate custom duration
        if (state.selectedChip == "Custom") {
            val dur = state.customDuration.toIntOrNull()
            if (dur == null || dur <= 0) {
                _uiState.update { it.copy(customDurationError = "Enter a valid duration greater than 0") }
                isValid = false
            }
        }

        // Validate reminder days < interval days
        val reminderDays = state.reminderDaysBefore.toIntOrNull()
        val intervalDays = resolveIntervalDays(state)
        if (reminderDays == null || reminderDays <= 0) {
            _uiState.update { it.copy(reminderDaysError = "Enter a valid number of days") }
            isValid = false
        } else if (intervalDays > 0 && reminderDays >= intervalDays) {
            _uiState.update {
                it.copy(reminderDaysError = "Must be less than interval ($intervalDays days)")
            }
            isValid = false
        }

        return isValid
    }

    private fun validateAndUpdateSaveState() {
        // Light real-time validation — just update button enabled state
        val state = _uiState.value
        val customOk = if (state.selectedChip == "Custom") {
            (state.customDuration.toIntOrNull() ?: 0) > 0
        } else true

        val reminderOk = (state.reminderDaysBefore.toIntOrNull() ?: 0) > 0

        _uiState.update { it.copy(isSaveEnabled = customOk && reminderOk) }
    }

    // Resolves how many days the current selection represents
    private fun resolveIntervalDays(state: EditCheckInUiState): Int {
        return if (state.selectedChip == "Custom") {
            val dur = state.customDuration.toIntOrNull() ?: 0
            when (state.customUnit) {
                "Weeks"  -> dur * 7
                "Months" -> dur * 30
                else     -> dur
            }
        } else {
            chipToDays[state.selectedChip] ?: 7
        }
    }
}