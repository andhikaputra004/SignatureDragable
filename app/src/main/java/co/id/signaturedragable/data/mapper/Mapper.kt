package co.id.signaturedragable.data.mapper

import co.id.signaturedragable.data.PDFResponseData
import co.id.signaturedragable.data.PDFResponseDataDTO


fun PDFResponseData.toDTO(): PDFResponseDataDTO {
    return PDFResponseDataDTO(file, name)
}