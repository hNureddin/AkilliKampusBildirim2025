package com.hne.akillikampusbildirim01

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MapReportAdapter(
    private val items: List<Report>
) : RecyclerView.Adapter<MapReportAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val btnOpen: Button = itemView.findViewById(R.id.btnOpenInMaps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_map_report, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val r = items[position]
        holder.tvTitle.text = r.title
        holder.tvStatus.text = r.status
        holder.tvLocation.text = "Konum: ${r.locationName} (${r.lat}, ${r.lng})"

        holder.btnOpen.setOnClickListener {
            val uri = Uri.parse("geo:${r.lat},${r.lng}?q=${r.lat},${r.lng}(${Uri.encode(r.locationName)})")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            // افتح Google Maps إن وجد، وإلا أي تطبيق خرائط
            intent.setPackage("com.google.android.apps.maps")
            if (intent.resolveActivity(holder.itemView.context.packageManager) != null) {
                holder.itemView.context.startActivity(intent)
            } else {
                holder.itemView.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
