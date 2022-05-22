package com.example.chatdemo.logic.repository

import android.content.Context
import androidx.lifecycle.liveData
import com.example.chatdemo.logic.data.dao.LocalUserDao
import com.example.nettyserver.data.database.AppDatabase
import com.example.nettyserver.data.entity.Friend
import com.example.nettyserver.data.entity.UserInfo
import kotlinx.coroutines.Dispatchers

object Repository {

    fun insertUser(context: Context,user: UserInfo) = liveData<Result<Long>>(Dispatchers.IO) {
        val result = try {
            val appDatabase = AppDatabase.getDatabase(context)
            val userId = appDatabase.userDao().insertUser(user)
            Result.success(userId)
        }catch (e: Exception){
            Result.failure<Long>(e)
        }
        emit(result)
    }

    fun checkUserInfo(context: Context, userId: Long) = liveData<Result<List<UserInfo>>>(Dispatchers.IO) {
        val result = try {
            val appDatabase = AppDatabase.getDatabase(context)
            val userList = appDatabase.userDao().checkUserInfo(userId)
            Result.success(userList)
        }catch (e: Exception){
            Result.failure<List<UserInfo>>(e)
        }
        emit(result)
    }

    fun loadAllFriends(context: Context, userId: Long) = liveData<Result<List<UserInfo>>>(Dispatchers.IO){
        val result = try {
            val appDatabase = AppDatabase.getDatabase(context)
            val allFriendsList = appDatabase.friendDao().loadAllFriends(userId)
            Result.success(allFriendsList)
        }catch (e: Exception){
            Result.failure<List<UserInfo>>(e)
        }
        emit(result)
    }

    fun isUserFriend(context: Context,userId: Long,friendId: Long) = liveData<Result<List<Friend>>>(Dispatchers.IO){
        val result = try {
            val appDatabase = AppDatabase.getDatabase(context)
            val allFriendsList = appDatabase.friendDao().isUserFriend(userId,friendId)
            Result.success(allFriendsList)
        }catch (e: Exception){
            Result.failure<List<Friend>>(e)
        }
        emit(result)
    }

    fun isUserSave() = LocalUserDao.isUserSaved()

    fun saveUser(user: UserInfo){
        LocalUserDao.saveUser(user)
    }

    fun clearUserData(){
        LocalUserDao.clearUserData()
    }

    fun getUserData() = LocalUserDao.getUserData()

}