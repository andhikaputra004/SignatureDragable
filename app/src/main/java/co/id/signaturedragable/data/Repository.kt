package co.id.signaturedragable.data

import okhttp3.ResponseBody

class Repository(private val service: Service) {

    suspend fun getPDF(): List<PDFResponseData> {
        return service.getListPDF().data
    }

    suspend fun downloadPDF(url: String): ResponseBody {
        return service.getPDF(url)
    }
}