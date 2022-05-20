package com.example.nettyserver.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class GroupInfo(val owner_id: Long, val name: String
    , @PrimaryKey(autoGenerate = true) val group_id: Long = 0) : Parcelable
