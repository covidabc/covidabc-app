package com.ufabc.covidabc.features

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.covidabc.App
import com.ufabc.covidabc.R
import com.ufabc.covidabc.model.FirestoreDatabaseOperationListener
import com.ufabc.covidabc.model.features.InventoryLocationDAO
import kotlinx.android.synthetic.main.dialog_change_count.*
import kotlinx.android.synthetic.main.dialog_create_location.*

class ItemCountAdapter(private val itemCount: Map<String, Int>,
                       private val context: Context,
                       private val refPath: String) : RecyclerView.Adapter<ItemCountAdapter.ViewHolder>() {

    private var keyMap : List<String> = itemCount.keys.toList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView : TextView = itemView.findViewById(R.id.item_name_text_view)
        val itemCountTextView : TextView = itemView.findViewById(R.id.item_count_text_view)
        val itemCard : CardView = itemView.findViewById(R.id.item_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_count, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() : Int = itemCount.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = keyMap[position]
        val count = itemCount[item]

        holder.itemNameTextView.text = item
        holder.itemCountTextView.text = count.toString()
        holder.itemCard.setOnClickListener {
            showItemCountDialog(item, count!!)
        }
    }

    private fun showItemCountDialog(item : String, count : Int) {
        Dialog(this.context).apply {
            setCancelable(false)
            setContentView(R.layout.dialog_change_count)

            item_name_text_view.text = context.getString(R.string.item_is, item)
            item_curr_count_message.text = context.getString(R.string.item_count_is, count)
            val maxValue : Int = 50
            val minValue : Int = -50

            item_count_picker.minValue = 0
            item_count_picker.maxValue = (maxValue - minValue)
            item_count_picker.value = (maxValue + minValue) / 2
            item_count_picker.setFormatter { index -> (index + minValue).toString() }

            quit_change_count_button.setOnClickListener {
                dismiss()
            }

            continue_change_count_button.setOnClickListener {
                val change = item_count_picker.value - minValue

                InventoryLocationDAO.updateItemCount(refPath, item, count, change,
                    object : FirestoreDatabaseOperationListener<Boolean> {
                        override fun onCompleted(sucess: Boolean) {
                            Toast.makeText(context, when (sucess) {
                                true ->  R.string.change_sucess
                                false -> R.string.change_failure
                            }, Toast.LENGTH_LONG).show()

                            dismiss()
                        }
                    })
            }
        }.show()
    }
}