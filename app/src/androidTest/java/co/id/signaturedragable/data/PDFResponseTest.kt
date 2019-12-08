package co.id.signaturedragable.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PDFResponseTest {

    private lateinit var pdfResponseData: PDFResponseData

    private lateinit var pdfResponse: PDFResponse

    @Before
    fun setUp() {
        pdfResponseData = PDFResponseData("test", "test1")

        pdfResponse = PDFResponse(listOf(pdfResponseData), 200)
    }

    @Test
    fun shouldGivesEqualsResult() {
        val cpyPDFResponse = pdfResponse
        assertTrue("Should result true", cpyPDFResponse == pdfResponse)

    }

    @Test
    fun shouldEqualsString() {
        val str = pdfResponse.toString()
        assertEquals("Should equals string form", str, pdfResponse.toString())

    }

    @Test
    fun shouldEqualsHashCode() {
        val cpyPdfResponse = pdfResponse
        assertEquals(
            "should equals hashcode",
            cpyPdfResponse.hashCode(),
            pdfResponse.hashCode()
        )
    }

    @Test
    fun shouldReturnCopyObject() {
        val cpyPdfResponse = pdfResponse.copy()
        assertEquals(
            "should equals same value data",
            pdfResponse.data,
            cpyPdfResponse.data
        )

        assertEquals(
            "should equals same value status",
            pdfResponse.status,
            cpyPdfResponse.status
        )
    }

    @Test
    fun shouldEqualsComponentProperties() {
        assertEquals(
            "should equal data model",
            pdfResponse.data,
            pdfResponse.component1()
        )
        assertEquals(
            "should equal status model",
            pdfResponse.status,
            pdfResponse.component2()
        )
    }

}