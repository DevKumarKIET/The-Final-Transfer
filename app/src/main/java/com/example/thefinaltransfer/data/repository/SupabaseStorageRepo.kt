package com.example.thefinaltransfer.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.thefinaltransfer.data.remote.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlin.time.Duration.Companion.seconds

class SupabaseStorageRepository {
    private val storage = SupabaseClient.client.storage
    private val bucket = storage.from("user-files")

    suspend fun uploadFile(
        context: Context,
        fileUri: Uri,
        userId: String,
        category: String,
        packetTitle: String
    ): AuthResult {
        return try {
            var fileName: String? = null
            context.contentResolver.query(fileUri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        fileName = cursor.getString(nameIndex)
                    }
                }
            }
            if (fileName == null) {
                fileName = "file_${System.currentTimeMillis()}"
            }

            val bytes = context.contentResolver.openInputStream(fileUri)?.readBytes()
                ?: return AuthResult.Error("Could not read file data")

            val path = "user_$userId/$category/$packetTitle/$fileName"
            bucket.upload(path, bytes){ upsert = true}
            AuthResult.Success("Success")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to upload file")
        }
    }

    suspend fun uploadMultipleFiles(
        context: Context,
        fileUris: List<Uri>,
        userId: String,
        category: String,
        packetTitle: String
    ): List<AuthResult> {
        return try {
            fileUris.map { uri ->
                uploadFile(context, uri, userId, category, packetTitle)
            }
        } catch (e: Exception) {
            listOf(AuthResult.Error(e.message ?: "Failed multiple upload"))
        }
    }

    suspend fun listFiles(
        userId: String,
        category: String,
        packetTitle: String
    ): List<String> {
        return try {
            val path = "user_$userId/$category/$packetTitle"
            val bucketItems = bucket.list(path)
            bucketItems.map { it.name }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFileUrl(
        userId: String,
        category: String,
        packetTitle: String,
        fileName: String
    ): String? {
        return try {
            val path = "user_$userId/$category/$packetTitle/$fileName"
            bucket.createSignedUrl(path, expiresIn = 3600.seconds)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun downloadFile(
        userId: String,
        category: String,
        packetTitle: String,
        fileName: String
    ): ByteArray? {
        return try {
            val path = "user_$userId/$category/$packetTitle/$fileName"
            bucket.downloadAuthenticated(path)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteFile(
        userId: String,
        category: String,
        packetTitle: String,
        fileName: String
    ): AuthResult {
        return try {
            val path = "user_$userId/$category/$packetTitle/$fileName"
            bucket.delete(path)
            AuthResult.Success("Deleted")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Failed to delete file")
        }
    }
}
