package com.example.nettyserver.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["user_id","group_id"])
@Parcelize
data class Group(val user_id: Long, val group_id: Long) : Parcelable
