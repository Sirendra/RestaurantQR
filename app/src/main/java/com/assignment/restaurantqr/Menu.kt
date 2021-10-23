package com.assignment.restaurantqr

import com.assignment.restaurantqr.Adapters.ListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.restaurantqr.Models.Lists
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {
    lateinit var reference: DatabaseReference
    lateinit var data: MutableList<Lists>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        var userUID = FirebaseAuth.getInstance().currentUser!!.uid
        reference = FirebaseDatabase.getInstance().getReference(userUID).child("MenuList")
        data = mutableListOf()
        addMenu.setOnClickListener {
            var intent=Intent(applicationContext,CreateMenu::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        reference.get().addOnSuccessListener {
            data.clear()
            it.children.forEach { i ->
                var item = i.key.toString()
                var price = it.child(item).value.toString()
                data.add(Lists(item, price))
            }
            if (data != null) {
                emptyText.visibility = View.GONE
                val linearLayoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
                menuList.layoutManager = linearLayoutManager
                var adapter1 = ListAdapter(data, this)
                menuList.adapter = adapter1
            } else {
                emptyText.visibility = View.VISIBLE
                menuList.visibility = View.INVISIBLE
            }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }
}