package com.assignment.restaurantqr.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.restaurantqr.Models.OrderData
import com.assignment.restaurantqr.R

class OrderAdapter (
        private val rssObject: MutableList<OrderData>,
        private val mContext: Context,
) : RecyclerView.Adapter<OrderAdapter.FeedViewHolders>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_order, parent, false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }
    //giving data to card
    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = rssObject[position].item
        holder.txtTitle1.text = rssObject[position].quantity

    }

    override fun getItemCount(): Int {
        return rssObject.size
    }
    //binding
    inner class FeedViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView){

        var txtTitle: TextView
        var txtTitle1: TextView


        init {

            txtTitle = itemView.findViewById(R.id.itemName)
            txtTitle1 = itemView.findViewById(R.id.itemQuantity)
        }
        //to get position of user's clicked data

    }
}