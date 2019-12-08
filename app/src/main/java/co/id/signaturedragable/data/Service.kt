package co.id.signaturedragable.data

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface Service {

    @GET("5d36642e5600006c003a52c1")
    suspend fun getListPDF(): PDFResponse

    @GET
    suspend fun getPDF(@Url url: String): ResponseBody
}