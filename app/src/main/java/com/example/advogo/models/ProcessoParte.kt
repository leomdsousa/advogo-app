package com.example.advogo.models

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProcessoParte(
    var nome: String? = null,
    var documento: String? = null,
    var contato: String? = null,
    var tipo: String? = null,
    @Exclude var tipoObj: TiposPartes? = null,
): Parcelable
