package com.example.chatdemo.logic.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.nettyserver.data.entity.Friend
import com.example.nettyserver.data.entity.UserInfo

@Dao
interface FriendDao {
    @Query("select * from UserInfo " +
                "where userId in (select id_friend from Friend " +
                "where id_my = :userId)")
    fun loadAllFriends(userId: Long) : List<UserInfo>

    @Query("select * from friend " +
            "where id_my = :userId and id_friend = :friendId")
    fun isUserFriend(userId: Long, friendId: Long): List<Friend>
}