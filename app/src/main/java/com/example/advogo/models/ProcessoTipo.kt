package com.example.advogo.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProcessoTipo(
    @DocumentId
    var id: String = "",
    var tipo: String? = null
): Parcelable {
}