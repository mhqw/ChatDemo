package com.example.nettyserver.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["id_my","id_friend"])
@Parcelize
data class Friend(val id_my: Long, val id_friend: Long) : Parcelable
