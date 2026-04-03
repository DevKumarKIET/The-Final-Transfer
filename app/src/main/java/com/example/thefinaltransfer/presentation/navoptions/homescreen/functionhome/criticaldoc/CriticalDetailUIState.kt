package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc

import com.example.thefinaltransfer.data.model.FileItem
import com.example.thefinaltransfer.data.model.FileType
import com.example.thefinaltransfer.data.model.TrustedNomineeItem


data class PacketDetailUiState(

    // ── Packet info ───────────────────────────────────────────────────────────
    val packetTitle: String   = "Critical Documents",
    val vaultType: String     = "Emergency Packet",
    val fileCount: Int        = 5,
    val status: String        = "Active",
    val createdDate: String   = "Dec 10",

    // ── Notes ─────────────────────────────────────────────────────────────────
    val notes: String = "These are critical legal and identity documents that must be " +
            "handed over to Dr. Deepa immediately upon activation. Please ensure all " +
            "documents are verified with the legal authority before proceeding.",

    // ── Primary assigned nominee ──────────────────────────────────────────────
    val nomineeName: String         = "Dr. Deepa",
    val nomineeRelationship: String = "Family",
    val nomineeEmail: String        = "deepa@email.com",
    val nomineePhone: String        = "+1 (555) 123-4567",
    val nomineeAddedDate: String    = "Added on Dec 10, 2025",

    // ── Files ─────────────────────────────────────────────────────────────────
    val files: List<FileItem> = listOf(
        FileItem("ID_Proof.pdf", "2.3 MB", FileType.PDF),
        FileItem("Property_Deed.pdf", "5.1 MB", FileType.PDF),
        FileItem("Family_Photo.jpg", "3.2 MB", FileType.IMAGE),
        FileItem("Will_Document.pdf", "1.8 MB", FileType.PDF),
        FileItem("Insurance_Policy.pdf", "4.5 MB", FileType.PDF)
    ),

    // ── Trusted nominees ──────────────────────────────────────────────────────
    val trustedNominees: List<TrustedNomineeItem> = listOf(
        TrustedNomineeItem("Harshil", "harshil@email.com", true),
        TrustedNomineeItem("Devvaaa", "devvaa@email.com", true)
    ),

    // ── Trigger settings ──────────────────────────────────────────────────────
    val triggerType: String       = "Inactivity Based",
    val checkInInterval: String   = "7 Days",
    val missedThreshold: String   = "3 Check-ins",
    val triggerStatus: String     = "Armed & Watching",

    // ── Dialog ────────────────────────────────────────────────────────────────
    val showDeleteDialog: Boolean = false
)