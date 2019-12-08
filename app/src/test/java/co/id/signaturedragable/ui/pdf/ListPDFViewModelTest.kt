package co.id.signaturedragable.ui.pdf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.id.signaturedragable.data.PDFResponseData
import co.id.signaturedragable.data.Repository
import co.id.signaturedragable.utils.CoroutineTestRule
import co.id.signaturedragable.utils.mock
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ListPDFViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private var repository = mock<Repository>()

    private lateinit var viewModel: ListPDFViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)

        viewModel = ListPDFViewModel(repository)
    }

    @Test
    fun `get response when call repository get PDF`() {
        val response = listOf<PDFResponseData>(PDFResponseData("1", "test1"))
        runBlocking {
            `when`(repository.getPDF()).thenReturn(response)
            viewModel.pdfData.value = response
            verify(repository).getPDF()
        }
    }

    @Test
    fun `get null when call repository get PDF`() {
        val response = emptyList<PDFResponseData>()
        runBlocking {
            `when`(repository.getPDF()).thenReturn(response)
            viewModel.pdfData.value = response
            verify(repository).getPDF()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}