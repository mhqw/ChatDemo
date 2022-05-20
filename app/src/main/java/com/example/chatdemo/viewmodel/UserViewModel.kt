package com.example.chatdemo.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.chatdemo.logic.repository.Repository
import com.example.nettyserver.data.entity.UserInfo

@SuppressLint("StaticFieldLeak")
class UserViewModel : ViewModel() {

    lateinit var context: Context

    private val insertUserLiveData = MutableLiveData<UserInfo>()
    private val checkUserInfoLiveData = MutableLiveData<Long>()

    var newUserId: Long = -1L
    val checkUserList = ArrayList<UserInfo>()

    val newUserIdLiveData = Transformations.switchMap(insertUserLiveData){ newUser->
        Repository.insertUser(context,newUser)
    }
    val checkUserListLiveData = Transformations.switchMap(checkUserInfoLiveData){ userId->
        Repository.checkUserInfo(context,userId)
    }

    fun insertUser(newUser: UserInfo){
        insertUserLiveData.value = newUser
    }

    fun checkUserInfo(userId: Long){
        checkUserInfoLiveData.value = userId
    }

}