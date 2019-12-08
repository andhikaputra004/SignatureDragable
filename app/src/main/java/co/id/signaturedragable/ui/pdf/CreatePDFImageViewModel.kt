package co.id.signaturedragable.ui.pdf

import android.app.Application
import android.graphics.BitmapFactory
import android.webkit.URLUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.id.signaturedragable.data.Repository
import co.id.signaturedragable.utils.UiState
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okio.Okio
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class CreatePDFImageViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    val fileDownloaded = MutableLiveData<File>()

    val uiState = MutableLiveData<UiState>()

    val saveState = MutableLiveData<UiState>()

    var fileName = ""

    var page = 0

    var positionX = 0f

    var positionY = 0f

    fun downloadPDF(url: String) {
        viewModelScope.launch {
            try {
                uiState.postValue(UiState.Loading)
                val fileName = URLUtil.guessFileName(
                    url,
                    null,
                    null
                )
                val file = File(getApplication<Application>().cacheDir, fileName)
                repository.downloadPDF(url).let {
                    with(Okio.buffer(Okio.sink(file))) {
                        writeAll(it.source())
                        close()
                    }
                }
                fileDownloaded.value = file
                uiState.postValue(UiState.Success)
            } catch (error: Exception) {
                uiState.postValue(UiState.Error(error.message ?: "Something went wrong"))
            }
        }
    }

    fun addSignature(positionX: Float, positionY: Float) {
        GlobalScope.launch(Dispatchers.IO) {
            saveState.postValue(UiState.Loading)
            try {
                val mediaStorageDir = File(
                    getApplication<Application>().cacheDir,
                    fileName
                )

                val document = PDDocument.load(mediaStorageDir)

                val image = File(getApplication<Application>().cacheDir, "preview.jpg")
                val bitmap = BitmapFactory.decodeStream(FileInputStream(image))
                val img = LosslessFactory.createFromImage(document, bitmap)
                val contentStream =
                    PDPageContentStream(document, document.getPage(page), true, true, true)
                contentStream.drawImage(img, positionX / 3, positionY / 3, 150f, 150f)

                contentStream.close()
                document.save(mediaStorageDir.path)
                document.close()
                saveState.postValue(UiState.Success)
            } catch (e: Exception) {
                saveState.postValue(UiState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
}