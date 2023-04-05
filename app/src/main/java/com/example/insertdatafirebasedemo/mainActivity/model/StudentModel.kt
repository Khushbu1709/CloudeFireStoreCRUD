package com.example.insertdatafirebasedemo.mainActivity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class StudentModel(var name:String,var email:String,var password:String ):Parcelable
