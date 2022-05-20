package com.example.chatdemo.logic.repository

import android.content.Context
import androidx.lifecycle.liveData
import com.example.nettyserver.data.database.AppDatabase
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

}