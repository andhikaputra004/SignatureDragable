package co.id.signaturedragable.ui.pdf

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.signaturedragable.R
import co.id.signaturedragable.data.PDFResponseData
import co.id.signaturedragable.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_pdf.*

class PDFAdapter(private val onClick: (PDFResponseData) -> Unit) :
    RecyclerView.Adapter<PDFAdapter.ViewHolder>() {


    var items: List<PDFResponseData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_pdf))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener { onClick(items[adapterPosition]) }
        }

        fun bind(model: PDFResponseData) {
            tv_label_content.text = model.name
        }
    }
}