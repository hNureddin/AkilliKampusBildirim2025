package com.hne.akillikampusbildirim01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(
    private val items: List<Report>,
    private val isAdmin: Boolean
): RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtVwTitle)
        val description: TextView = itemView.findViewById(R.id.txtVwDescription)
        val status: TextView = itemView.findViewById(R.id.txtVwStatus)
        val time: TextView = itemView.findViewById(R.id.txtVwTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = items[position]
        holder.title.text = report.title
        holder.description.text = report.description
        holder.status.text = report.status
        holder.time.text = report.time

        holder.itemView.setOnClickListener {
            val f = ReportDetailsFragment.newInstance(position)
            (holder.itemView.context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, f)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = items.size

    private fun nextStatus(current: String): String {
        return when (current) {
            "Status: Open" -> "Status: progress"
            "Status: progress" -> "Status: Solved"
            else -> "Status: Open"
        }
    }
}