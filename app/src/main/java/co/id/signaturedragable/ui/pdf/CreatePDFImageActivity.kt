package co.id.signaturedragable.ui.pdf

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import co.id.signaturedragable.R
import co.id.signaturedragable.data.PDFResponseDataDTO
import co.id.signaturedragable.utils.Constant.PDF_URL
import co.id.signaturedragable.utils.UiState
import co.id.signaturedragable.utils.guessFileName
import co.id.signaturedragable.utils.observe
import co.id.signaturedragable.utils.showToast
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader
import kotlinx.android.synthetic.main.activity_create_pdf_image.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class CreatePDFImageActivity : AppCompatActivity(), View.OnDragListener, View.OnTouchListener {

    private val viewModel: CreatePDFImageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pdf_image)
        cl_drag.setOnDragListener(this)
        iv_signature.setOnTouchListener(this)

        val extras = intent.extras?.get(PDF_URL) as PDFResponseDataDTO?
        PDFBoxResourceLoader.init(applicationContext);

        viewModel.fileName = extras?.file?.guessFileName() ?: ""

        val file = File(this.cacheDir, extras?.file?.guessFileName() ?: "")


        loadImageFromStorage()


        if (file.exists() || file.isFile) {

            pdf_view.fromFile(file)
                .scrollHandle(DefaultScrollHandle(this))
                .onPageChange { page, pageCount ->
                    viewModel.page = page
                }
                .onError {
                    textFailed.isVisible = true
                }
                .load()
        } else {
            viewModel.downloadPDF(extras?.file ?: "")
        }



        observe(viewModel.uiState) {
            progressBar.isVisible = UiState.Loading == it
            textFailed.isVisible = it is UiState.Error
            if (it is UiState.Error) {
                this.showToast(it.message)
            }
        }

        observe(viewModel.fileDownloaded) {
            pdf_view.fromFile(it)
                .scrollHandle(DefaultScrollHandle(this))
                .onPageChange { page, pageCount ->
                    viewModel.page = page
                }
                .onError {
                    textFailed.isVisible = true
                }
                .load()
        }

        observe(viewModel.saveState) {
            if (it is UiState.Success) {
                this.showToast("Add Image")
                finish()
            }
            if (it is UiState.Loading) {
                iv_signature.setOnTouchListener(null)
                pdf_view.isEnabled = false
            }
            progressBar.isVisible = UiState.Loading == it
            textFailed.isVisible = it is UiState.Error
            if (it is UiState.Error) {
                this.showToast(it.message)
            }
        }
    }

    private fun loadImageFromStorage() {
        try {
            val f = File(this.cacheDir, "preview.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            iv_signature.setImageBitmap(b)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun onDrag(view: View?, dragEvent: DragEvent?): Boolean {

        when (dragEvent?.action) {
            DragEvent.ACTION_DRAG_ENDED -> {
                viewModel.addSignature(viewModel.positionX, viewModel.positionY)
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                return true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                return true
            }
            DragEvent.ACTION_DRAG_STARTED -> {
                return true
            }
            DragEvent.ACTION_DROP -> {
                val tvState = dragEvent.localState as View
                val tvParent = tvState.parent as ViewGroup
                tvParent.removeView(tvState)
                val container = view as ConstraintLayout
                container.addView(tvState)
                tvParent.removeView(tvState)
                tvState.x = dragEvent.x
                tvState.y = dragEvent.y
                viewModel.positionX = tvState.x
                viewModel.positionY = tvState.y
                view.addView(tvState)
                view.setVisibility(View.VISIBLE)
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                return true
            }
            else -> return false
        }
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        return if (motionEvent?.action === MotionEvent.ACTION_DOWN) {
            val dragShadowBuilder = View.DragShadowBuilder(view)
            view?.startDragAndDrop(null, dragShadowBuilder, view, 0)
            true
        } else {
            false
        }
    }
}