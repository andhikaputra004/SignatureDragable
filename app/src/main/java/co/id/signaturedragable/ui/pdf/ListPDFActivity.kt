package co.id.signaturedragable.ui.pdf

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.signaturedragable.R
import co.id.signaturedragable.data.mapper.toDTO
import co.id.signaturedragable.utils.Constant.PDF_URL
import co.id.signaturedragable.utils.UiState
import co.id.signaturedragable.utils.observe
import co.id.signaturedragable.utils.showToast
import kotlinx.android.synthetic.main.activity_list_pdf.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ListPDFActivity : AppCompatActivity() {

    private val viewModel: ListPDFViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pdf)

        setupView()
    }

    private fun setupView() {

        val signature = File(this.cacheDir, "preview.jpg")


        val pdfAdapter = PDFAdapter {
            if (!signature.exists() || !signature.isFile) {
                showToast("Please create signature first")
            } else {
                startActivity(
                    Intent(this, CreatePDFImageActivity::class.java)
                        .putExtra(PDF_URL, it.toDTO())
                )
            }
        }

        with(rv_pdf) {
            layoutManager =
                LinearLayoutManager(this@ListPDFActivity, LinearLayoutManager.VERTICAL, false)
            adapter = pdfAdapter
        }

        observe(viewModel.pdfData) {
            pdfAdapter.items = it ?: emptyList()
        }

        observe(viewModel.uiState) {
            progressBar.isVisible = it == UiState.Loading
            if (it is UiState.Error) {
                showToast(it.message)
            }
        }
    }
}