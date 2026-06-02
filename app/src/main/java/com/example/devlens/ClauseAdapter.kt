package com.example.devlens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClauseAdapter(
    private val clauses: List<ClauseAnalysis>
) : RecyclerView.Adapter<ClauseAdapter.ClauseViewHolder>() {

    class ClauseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvRisk: TextView = view.findViewById(R.id.tvRisk)
        val tvSummary: TextView = view.findViewById(R.id.tvSummary)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClauseViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clause, parent, false)

        return ClauseViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ClauseViewHolder,
        position: Int
    ) {
        val clause = clauses[position]

        holder.tvTitle.text = clause.title
        holder.tvRisk.text = clause.riskLevel
        holder.tvSummary.text = clause.summary
    }

    override fun getItemCount(): Int {
        return clauses.size
    }
}