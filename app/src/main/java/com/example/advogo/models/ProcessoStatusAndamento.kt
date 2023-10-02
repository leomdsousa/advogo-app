package com.example.advogo.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProcessoStatusAndamento(
    @DocumentId
    var id: String = "",
    var status: String? = null,
    var ativo: Boolean? = true,
    @Exclude var selecionado: Boolean? = null,
): Parcelable {
}