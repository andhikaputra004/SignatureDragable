package co.id.signaturedragable.utils

sealed class UiState {
    object Refreshing : UiState()
    object Loading : UiState()
    object Success : UiState()
    class Error(val message: String) : UiState()

    companion object {
        fun createLiveData() = ActionLiveData<UiState> { it == UiState.Loading }
    }
}