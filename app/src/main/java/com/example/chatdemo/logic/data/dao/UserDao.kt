package com.example.nettyserver.data.dao

import androidx.room.*
import com.example.nettyserver.data.entity.Friend
import com.example.nettyserver.data.entity.GroupInfo
import com.example.nettyserver.data.entity.UserInfo

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserInfo) : Long

    @Delete
    fun deleteUser(user: UserInfo)

    @Update
    fun updateUser(newUser: UserInfo)

    @Query("select * from Userinfo " +
            "where userId = :userId"
    )
    fun checkUserInfo(userId: Long) : List<UserInfo>

    @Query(
        "select userId from UserInfo " +
                "where userId in (select id_friend from Friend " +
            "where id_my = :userId)")
    fun loadAllFriend(userId: Long) : List<Long>

    @Query("select * from friend " +
            "where id_my = :user_id and id_friend = :friendId")
    fun isUserFriend(user_id: Long, friendId: Long): List<Friend>
}