package com.example.insertdatafirebasedemo.mainActivity.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.insertdatafirebasedemo.R
import com.example.insertdatafirebasedemo.databinding.ActivityMainBinding
import com.example.insertdatafirebasedemo.mainActivity.viewHolder.StudentViewHolder
import com.example.insertdatafirebasedemo.readActivity.ReadDataActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var studentVieModel: StudentViewHolder
    lateinit var db: FirebaseFirestore
    private var type: Int? = null
    private var position = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        studentVieModel = ViewModelProvider(this)[StudentViewHolder::class.java]
        setContentView(binding.root)

        binding.studentViewHolder = studentVieModel
        db = Firebase.firestore

        if (intent.extras != null) {
            type = intent.extras!!.getInt("type")
            position = intent.extras!!.getInt("position")

        }
        if (intent.extras != null) {
            val name = intent.extras?.getString("name")
            val email = intent.extras?.getString("email")
            val pass = intent.extras?.getString("pass")

            studentVieModel.name = name.toString()
            studentVieModel.email = email.toString()
            studentVieModel.password = pass.toString()

            binding.insertData.text = getString(R.string.update)
            binding.btnInsert.text = getString(R.string.update)

            Log.d("PASS", pass.toString())
        }

        binding.btnUpdate.setOnClickListener {
            startActivity(Intent(this, ReadDataActivity::class.java))
        }

        binding.btnInsert.setOnClickListener {
            if (studentVieModel.registerValidation()) {
                if (type == 0) {
                    studentVieModel.updateData(position, db)
                } else {
                    studentVieModel.dataAdd(db)
                }
                val intent = Intent(this, ReadDataActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "please enter value", Toast.LENGTH_SHORT).show()
            }

        }
    }

}