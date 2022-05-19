package com.example.nettyserver.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class UserInfo(val email: String,val password: String,val name: String) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var user_id: Long = 0
}