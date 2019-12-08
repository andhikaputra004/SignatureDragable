package co.id.signaturedragable.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PDFResponse(
    @Json(name = "data")
    val data: List<PDFResponseData>,
    @Json(name = "status")
    val status: Int?
)

@JsonClass(generateAdapter = true)
data class PDFResponseData(
    @Json(name = "file")
    val file: String,
    @Json(name = "name")
    val name: String?
)