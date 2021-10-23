package com.assignment.restaurantqr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //QR generating
        var userUID= FirebaseAuth.getInstance().currentUser!!.uid
        val writer= QRCodeWriter()
        try {
            val bMax=writer.encode(userUID,BarcodeFormat.QR_CODE,512,512)
            val bitmap=Bitmap.createBitmap(bMax.width,bMax.height,Bitmap.Config.RGB_565)
            for (x in 0 until bMax.width){
                for (y in 0 until  bMax.height){
                    bitmap.setPixel(x,y,if (bMax[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            qrCode.setImageBitmap(bitmap)
        }catch (e:WriterException){
            e.printStackTrace()
        }
        signoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent= Intent(applicationContext,PhoneNoVerify::class.java)
            startActivity(intent)
        }
        menuBtn.setOnClickListener {
            var intent= Intent(applicationContext,Menu::class.java)
            startActivity(intent)
        }
        orderBtn.setOnClickListener {
            var intent= Intent(applicationContext,Order::class.java)
            startActivity(intent)
        }
    }
}