package com.example.chatdemo.logic.data.dao

import androidx.room.*
import com.example.nettyserver.data.entity.GroupInfo

@Dao
interface GroupDao {
    @Insert
    fun insertGroup(group: GroupInfo) : Long

    @Delete
    fun deleteGroup(group: GroupInfo)

    @Update
    fun updateGroup(newGroup: GroupInfo)

    @Query("select group_id from GroupInfo " +
            "where group_id in (select group_id from `Group` " +
            "where user_id = :userId)")
    fun loadAllGroup(userId: Long) : List<Long>
}