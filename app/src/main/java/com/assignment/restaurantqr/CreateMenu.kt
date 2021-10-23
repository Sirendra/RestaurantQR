package com.assignment.restaurantqr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_menu.*

class CreateMenu : AppCompatActivity() {
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_menu)
        var userUID = FirebaseAuth.getInstance().currentUser!!.uid
        reference = FirebaseDatabase.getInstance().getReference(userUID).child("MenuList")
        addMenuBtn.setOnClickListener {
            var name= menuName.text.toString().trim()
            var price= menuPrice.text.toString().trim()
            reference.child(name).setValue(price).addOnSuccessListener {
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Check your Internet Connection",Toast.LENGTH_SHORT).show()
            }
        }
        back_btn.setOnClickListener {
            finish()
        }

    }
}