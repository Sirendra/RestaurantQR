package com.assignment.restaurantqr.Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.restaurantqr.Models.Lists
import com.assignment.restaurantqr.R

class ListAdapter(
        private val rssObject: MutableList<Lists>,
        private val mContext: Context,
) : RecyclerView.Adapter<ListAdapter.FeedViewHolders>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolders {

        val itemView = inflater.inflate(R.layout.card_list, parent, false)
        return FeedViewHolders(itemView)
    }

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }
    //giving data to card
    override fun onBindViewHolder(holder: FeedViewHolders, position: Int) {
        holder.txtTitle.text = rssObject[position].item
        holder.txtTitle1.text = rssObject[position].price

    }

    override fun getItemCount(): Int {
        return rssObject.size
    }
    //binding
    inner class FeedViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView){

        var txtTitle: TextView
        var txtTitle1: TextView


        init {

            txtTitle = itemView.findViewById(R.id.item_name)
            txtTitle1 = itemView.findViewById(R.id.price)
        }
        //to get position of user's clicked data

    }
}
