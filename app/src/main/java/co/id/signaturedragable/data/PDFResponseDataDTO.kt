package co.id.signaturedragable.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PDFResponseDataDTO(
    val file: String,
    val name: String?
) : Parcelable