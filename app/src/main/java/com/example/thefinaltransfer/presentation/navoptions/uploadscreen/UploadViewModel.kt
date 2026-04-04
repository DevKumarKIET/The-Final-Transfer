package com.example.thefinaltransfer.presentation.navoptions.uploadscreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefinaltransfer.data.repository.AuthResult
import com.example.thefinaltransfer.data.repository.SupabaseStorageRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UploadUiState(
    val isUploading: Boolean = false,
    val uploadProgress: String = "",
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val uploadedCount: Int = 0,
    val totalCount: Int = 0
)

class UploadViewModel : ViewModel() {

    private val repository = SupabaseStorageRepository()
    private val _uploadState = MutableStateFlow(UploadUiState())
    val uploadState: StateFlow<UploadUiState> = _uploadState.asStateFlow()

    fun uploadPacket(
        context: Context,
        category: String,
        packetTitle: String,
        fileUris: List<Uri>
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            _uploadState.update { it.copy(errorMessage = "User not logged in") }
            return
        }

        viewModelScope.launch {
            _uploadState.update {
                it.copy(
                    isUploading = true,
                    totalCount = fileUris.size,
                    errorMessage = null,
                    successMessage = null
                )
            }

            var successCount = 0
            val failedErrors = mutableListOf<String>()

            for (i in fileUris.indices) {
                _uploadState.update { it.copy(uploadProgress = "Uploading ${i + 1}/${fileUris.size}...") }

                try {
                    val result = repository.uploadFile(context, fileUris[i], uid, category, packetTitle)
                    when (result) {
                        is AuthResult.Success -> successCount++
                        is AuthResult.Error -> failedErrors.add(result.message)
                        is AuthResult.Loading -> { }
                    }
                } catch (e: Exception) {
                    failedErrors.add(e.message ?: "Unknown error")
                }
            }

            _uploadState.update { state ->
                val successMsg = if (successCount > 0) "Uploaded $successCount file(s) successfully" else null
                val errorMsg = if (failedErrors.isNotEmpty()) failedErrors.joinToString("\n") else null

                state.copy(
                    isUploading = false,
                    uploadProgress = "",
                    uploadedCount = successCount,
                    successMessage = successMsg,
                    errorMessage = errorMsg
                )
            }
        }
    }

    fun clearMessages() {
        _uploadState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}
