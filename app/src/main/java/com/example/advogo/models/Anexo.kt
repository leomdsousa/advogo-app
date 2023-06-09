package com.example.advogo.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anexo(
    @DocumentId
    var id: String = "",
    var nome: String? = null,
    var uri: String? = null,
    var descricao: String? = null
): Parcelable {
}