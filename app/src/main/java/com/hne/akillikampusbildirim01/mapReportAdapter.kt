package com.hne.akillikampusbildirim01

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MapReportAdapter(private val items: List<Report>) : RecyclerView.Adapter<MapReportAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvType: TextView = v.findViewById(R.id.tvType)
        val tvStatus: TextView = v.findViewById(R.id.tvStatus)
        val tvCoords: TextView = v.findViewById(R.id.tvCoords)
        val btnOpen: Button = v.findViewById(R.id.btnOpenInMaps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_map_report, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val r = items[position]
        holder.tvTitle.text = r.title
        holder.tvType.text = "Type: ${r.type}"
        holder.tvStatus.text = r.status
        holder.tvCoords.text = "Location: ${"%.5f".format(r.lat)}, ${"%.5f".format(r.lng)}"

        holder.btnOpen.setOnClickListener {
            val uri = Uri.parse("geo:${r.lat},${r.lng}?q=${r.lat},${r.lng}(${Uri.encode(r.title)})")
            val intent = Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps")
            val ctx = holder.itemView.context
            if (intent.resolveActivity(ctx.packageManager) != null) ctx.startActivity(intent)
            else ctx.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun getItemCount(): Int = items.size
}
