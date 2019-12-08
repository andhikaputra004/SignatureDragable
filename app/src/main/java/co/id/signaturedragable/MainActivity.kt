package co.id.signaturedragable

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.id.signaturedragable.ui.pdf.ListPDFActivity
import co.id.signaturedragable.ui.signature.CreateSignatureActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_create_signature.setOnClickListener {
            startActivity(Intent(this, CreateSignatureActivity::class.java))
        }

        btn_create_pdf.setOnClickListener {
            startActivity(Intent(this, ListPDFActivity::class.java))
        }
    }
}
