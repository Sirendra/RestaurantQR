package com.assignment.restaurantqr

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.restaurantqr.Adapters.OrderAdapter
import com.assignment.restaurantqr.Models.OrderData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_order.*

class Order : AppCompatActivity() {
    lateinit var reference: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener
    lateinit var data: MutableList<OrderData>
    var tableNumber="0";
    var status="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        var userUID = FirebaseAuth.getInstance().currentUser!!.uid
        reference = FirebaseDatabase.getInstance().getReference(userUID).child("OrderList")
        data = mutableListOf()
        statusBar.visibility=View.GONE
        tableBar.visibility=View.GONE

        valueEventListener=reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(it: DataSnapshot) {
                if(it.exists()){
                    statusBar.visibility=View.VISIBLE
                    tableBar.visibility=View.VISIBLE
                    receivedBtn.setBackgroundResource(R.color.white)
                    acceptedBtn.setBackgroundResource(R.color.white)
                    preparingBtn.setBackgroundResource(R.color.white)
                    readyBtn.setBackgroundResource(R.color.white)
                }else {
                    statusBar.visibility=View.GONE
                    tableBar.visibility=View.GONE
                }
                data.clear()
                run loop@{
                    it.children.forEach { i ->
                        //table no
                        tableNumber = i.key.toString()
                        tableNo.text = tableNumber
                        status = it.child(tableNumber).child("Status").value.toString()
                        when (status) {
                            "Received" -> receivedBtn.setBackgroundResource(R.color.purple_500)
                            "Accepted" -> acceptedBtn.setBackgroundResource(R.color.purple_500)
                            "Preparing" -> preparingBtn.setBackgroundResource(R.color.purple_500)
                            "Ready" -> readyBtn.setBackgroundResource(R.color.purple_500)
                        }
                        it.child(tableNumber).child("Item").children.forEach { v ->
                            var item = v.key.toString()
                            var quantity = it.child(tableNumber).child("Item").child(item).value.toString()
                            data.add(OrderData(item, quantity))
                        }
                        return@loop
                    }
                }
                if (data != null) {
                    val linearLayoutManager =
                            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
                    orderList.layoutManager = linearLayoutManager
                    var adapter1 = OrderAdapter(data, baseContext)
                    orderList.adapter = adapter1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext , "No Internet Connection", Toast.LENGTH_SHORT).show()
            }

        })

        orderBacKBtn.setOnClickListener {
            finish()
        }
    }

    fun onClick(v:View){
        receivedBtn.setBackgroundResource(R.color.white)
        acceptedBtn.setBackgroundResource(R.color.white)
        preparingBtn.setBackgroundResource(R.color.white)
        readyBtn.setBackgroundResource(R.color.white)
        if (tableNumber!="0" && status !="") {
            when (v.id) {
                R.id.receivedBtn -> {
                    receivedBtn.setBackgroundResource(R.color.purple_500)
                    reference.child(tableNumber).child("Status").setValue("Received")
                }
                R.id.acceptedBtn -> {
                    acceptedBtn.setBackgroundResource(R.color.purple_500)
                    reference.child(tableNumber).child("Status").setValue("Accepted")
                }
                R.id.preparingBtn -> {
                    preparingBtn.setBackgroundResource(R.color.purple_500)
                    reference.child(tableNumber).child("Status").setValue("Preparing")
                }
                R.id.readyBtn -> {
                    readyBtn.setBackgroundResource(R.color.purple_500)
                    reference.child(tableNumber).child("Status").setValue("Ready")
                }
                R.id.servedBtn -> {
                    receivedBtn.setBackgroundResource(R.color.white)
                    acceptedBtn.setBackgroundResource(R.color.white)
                    preparingBtn.setBackgroundResource(R.color.white)
                    readyBtn.setBackgroundResource(R.color.white)
                    reference.child(tableNumber).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (appleSnapshot in snapshot.children) {
                                appleSnapshot.ref.removeValue()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d("Error", "Failed to remove")
                        }
                    })
                }
            }
        }
    }

}