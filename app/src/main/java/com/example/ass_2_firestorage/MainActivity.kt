package com.example.ass_2_firestorage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    val reqCode:Int = 100
    lateinit var imagePath :Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val storage = Firebase.storage
        val ref = storage.reference

        chooseGalary.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, reqCode)

        }

        Upload.setOnClickListener{
            ref.child("image/"+UUID.randomUUID().toString())
                .putFile(imagePath)
                .addOnSuccessListener{
                    Toast.makeText("Success",Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{
                    Toast.makeText("Failure",Toast.LENGTH_SHORT).show()

                }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqCode){
            imagePath = data!!.data!!
            val bitMap = MediaStore.Images.Media.getBitmap(contentResolver,imagePath)
            img.setImageBitmap(bitMap)


        }
    }
}