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

}