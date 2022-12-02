package com.example.testapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val dataSet: ArrayList<ArrayList<String>>, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View, mainActivity: MainActivity) : RecyclerView.ViewHolder(view) {
        val numView: TextView
        val factView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            numView = view.findViewById(R.id.number_view)
            factView = view.findViewById(R.id.fact_view)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.single_history, viewGroup, false)

        return ViewHolder(view, mainActivity)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.numView.text = dataSet[position][0]
        viewHolder.factView.text = dataSet[position][1]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
