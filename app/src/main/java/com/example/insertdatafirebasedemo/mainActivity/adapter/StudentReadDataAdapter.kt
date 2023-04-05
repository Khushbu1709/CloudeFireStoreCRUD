package com.example.insertdatafirebasedemo.mainActivity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.insertdatafirebasedemo.databinding.ItemDataBinding
import com.example.insertdatafirebasedemo.mainActivity.model.StudentModel


class StudentReadDataAdapter(
    var context: Context,
    private var studentModel: ArrayList<StudentModel>,
    var callback: (position: Int, type: String) -> Unit
) : RecyclerView.Adapter<StudentReadDataAdapter.StudentReadDataViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentReadDataViewHolder {

        val sendData =
            ItemDataBinding.inflate(LayoutInflater.from(context), parent, false)
        return StudentReadDataViewHolder(sendData)

    }

    override fun onBindViewHolder(
        holder: StudentReadDataViewHolder,
        position: Int
    ) {
        holder.binding.txtName.text = studentModel[position].name
        holder.binding.txtEmail.text = studentModel[position].email

        holder.binding.imageEdit.setOnClickListener {
            callback.invoke(position, "update")
        }

        holder.binding.imageDelete.setOnClickListener {
            callback.invoke(position, "delete")
        }
    }

    override fun getItemCount(): Int = studentModel.size

    inner class StudentReadDataViewHolder(var binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}