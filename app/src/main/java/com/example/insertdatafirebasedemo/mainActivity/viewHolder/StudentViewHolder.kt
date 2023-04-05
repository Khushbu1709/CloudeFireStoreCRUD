package com.example.insertdatafirebasedemo.mainActivity.viewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.insertdatafirebasedemo.R
import com.example.insertdatafirebasedemo.mainActivity.model.StudentModel
import com.example.insertdatafirebasedemo.mainActivity.validation.isValidEmail
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentViewHolder @Inject constructor(private val context: Context) : ViewModel() {

    var name: String = ""
    var email: String = ""
    var password: String = ""

    var blankEmail = ObservableField("")
    var blankName = ObservableField("")
    var blankPassword = ObservableField("")


    fun registerValidation(): Boolean {

        if (name.isEmpty()) {
            blankName.set(context.resources.getString(R.string.name))
            return false
        }
        if (email.isEmpty()) {
            blankEmail.set(context.resources.getString(R.string.email))
            return false
        }
        if (!isValidEmail(email)) {
            blankEmail.set(context.resources.getString(R.string.current_email))
            return false
        }
        if (password.isEmpty()) {
            blankPassword.set(context.getString(R.string.password))
            return false
        }
        if (password.length < 6) {
            blankPassword.set(context.getString(R.string.current_password))
            return false
        }
        return true
    }

    @SuppressLint("SuspiciousIndentation")
    fun dataAdd(db: FirebaseFirestore) {


        val hashMap = hashMapOf("name" to name, "email" to email, "password" to password)

        var database = db.collection("users").document(name)
        database.set(hashMap).addOnSuccessListener {

        }.addOnFailureListener {
        }
    }

    fun updateData(position: Int?, db: FirebaseFirestore) {

        val updateCourse = StudentModel(name, email, password)

        db.collection("users").document(position.toString()).set(updateCourse)
            .addOnSuccessListener {
            }.addOnFailureListener {
        }

    }
}
