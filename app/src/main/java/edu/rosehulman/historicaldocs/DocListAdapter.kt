package edu.rosehulman.historicaldocs

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_view_doc.view.*

class DocListAdapter(var context: Context?, var listerner: DocListFragment.OnDocSelectedListener?) : RecyclerView.Adapter<DocViewHolder>() {

    var docs = ArrayList<Doc>()

    init {
       docs = DocUtils.loadDocs(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_view_doc, parent, false)
        return DocViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        holder.bind(docs[position])
    }
    override fun getItemCount() = docs.size

    fun selectDoc(position: Int) {
        listerner?.onDocSelected(docs[position])
    }
}