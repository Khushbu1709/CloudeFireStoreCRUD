package com.example.insertdatafirebasedemo.readActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.insertdatafirebasedemo.databinding.ActivityReadDataBinding
import com.example.insertdatafirebasedemo.mainActivity.activity.MainActivity
import com.example.insertdatafirebasedemo.mainActivity.model.StudentModel
import com.example.insertdatafirebasedemo.mainActivity.adapter.StudentReadDataAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ReadDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadDataBinding
    private lateinit var studentModel: ArrayList<StudentModel>
    lateinit var studentReadDataAdapter: StudentReadDataAdapter
    private lateinit var db: FirebaseFirestore

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        studentModel = arrayListOf()

        db = Firebase.firestore

        studentDataShow()
        btnInsertClick()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        studentReadDataAdapter = StudentReadDataAdapter(this, studentModel) { position, type ->
            if (type == "update") {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("name", studentModel[position].name)
                intent.putExtra("email", studentModel[position].email)
                intent.putExtra("pass", studentModel[position].password)
                intent.putExtra("type", 0)
                startActivity(intent)
            }
            if (type == "delete") {
                db.collection("users").document(studentModel[position].name).delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            studentModel.removeAt(position)
                            studentReadDataAdapter.notifyDataSetChanged()
                        }
                    }
            }

        }
        binding.recyclerView.adapter = studentReadDataAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun studentDataShow() {
        db.collection("users").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {

                    val list = queryDocumentSnapshots.documents
                    Log.d("List", list.toString())
                    for (d in list) {
                        studentModel.add(
                            StudentModel(
                                d.getString("name").toString(),
                                d.getString("email").toString(),
                                d.getString("password").toString()
                            )
                        )
                        if (studentModel.isEmpty()) {
                            binding.dataNotFound.visibility = View.VISIBLE
                        }
                    }
                    studentReadDataAdapter.notifyDataSetChanged()

                } else {
                    binding.dataNotFound.visibility = View.VISIBLE

                }
            }.addOnFailureListener {
            }
    }

    private fun btnInsertClick() {
        binding.insertData.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}