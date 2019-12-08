package co.id.signaturedragable.ui.pdf

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.signaturedragable.data.PDFResponseData
import co.id.signaturedragable.data.Repository
import co.id.signaturedragable.utils.UiState
import kotlinx.coroutines.launch

class ListPDFViewModel(private val repository: Repository) : ViewModel() {

    val pdfData = MutableLiveData<List<PDFResponseData>>()

    val uiState = MutableLiveData<UiState>()

    fun getPDF() {
        viewModelScope.launch {
            try {
                uiState.postValue(UiState.Loading)
                pdfData.value = repository.getPDF()
                uiState.postValue(UiState.Success)
            } catch (error: Exception) {
                uiState.postValue(UiState.Error(error.message ?: "Something went wrong"))
            }
        }
    }

    init {
        getPDF()
    }
}