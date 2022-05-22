package com.example.chatdemo.logic.data.dao

import android.content.Context
import androidx.core.content.edit
import com.example.chatdemo.utils.ChatDemoApplication
import com.example.nettyserver.data.entity.UserInfo
import com.google.gson.Gson

object LocalUserDao {

    fun saveUser(user: UserInfo){
        sharedPreferences().edit {
            putString("user", Gson().toJson(user))
        }
    }

    fun isUserSaved() = sharedPreferences().contains("user")

    fun clearUserData(){
        sharedPreferences().edit().clear().apply()
    }

    fun getUserData(): UserInfo{
        val userJson = sharedPreferences().getString("user","")
        return Gson().fromJson(userJson,UserInfo::class.java)
    }

    private fun sharedPreferences() = ChatDemoApplication.context
        .getSharedPreferences("chat_demo",Context.MODE_PRIVATE)

}