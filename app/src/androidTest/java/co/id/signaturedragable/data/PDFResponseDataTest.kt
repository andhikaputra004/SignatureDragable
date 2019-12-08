package co.id.signaturedragable.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PDFResponseDataTest {

    private lateinit var pdfResponseData: PDFResponseData

    @Before
    fun setUp() {
        pdfResponseData = PDFResponseData("test", "test1")
    }

    @Test
    fun shouldGivesEqualsResult() {
        val cpyPDFResponseData = pdfResponseData
        assertTrue("Should result true", cpyPDFResponseData == pdfResponseData)

    }

    @Test
    fun shouldEqualsString() {
        val str = pdfResponseData.toString()
        assertEquals("Should equals string form", str, pdfResponseData.toString())

    }

    @Test
    fun shouldEqualsHashCode() {
        val cpyPdfResponse = pdfResponseData
        assertEquals(
            "should equals hashcode",
            cpyPdfResponse.hashCode(),
            pdfResponseData.hashCode()
        )
    }

    @Test
    fun shouldReturnCopyObject() {
        val cpyAboutModel = pdfResponseData.copy()
        assertEquals("should equals same value file", pdfResponseData.file, cpyAboutModel.file)

        assertEquals("should equals same value name", pdfResponseData.name, cpyAboutModel.name)
    }

    @Test
    fun shouldEqualsComponentProperties() {
        assertEquals("should equal file model", pdfResponseData.file, pdfResponseData.component1())
        assertEquals("should equal name model", pdfResponseData.name, pdfResponseData.component2())
    }
}