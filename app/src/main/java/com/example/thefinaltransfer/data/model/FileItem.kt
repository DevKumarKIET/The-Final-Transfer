package com.example.thefinaltransfer.data.model

// ─── File type enum ───────────────────────────────────────────────────────────
enum class FileType { PDF, IMAGE, VIDEO, DOC }

// ─── Domain model for a file inside a packet ─────────────────────────────────
data class FileItem(
    val name: String,
    val size: String,
    val type: FileType
)

// ─── Domain model for a trusted nominee ──────────────────────────────────────
data class TrustedNomineeItem(
    val name: String,
    val email: String,
    val isVerified: Boolean = true
)