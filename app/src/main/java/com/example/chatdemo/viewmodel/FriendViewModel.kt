package com.example.chatdemo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.chatdemo.logic.repository.Repository
import com.example.nettyserver.data.entity.UserInfo

class FriendViewModel : ViewModel() {

    lateinit var context: Context

    private val loadAllFriendsLiveData = MutableLiveData<Long>()

    val allFriendsList = ArrayList<UserInfo>()

    val allFriendsLiveData = Transformations.switchMap(loadAllFriendsLiveData){ userId ->
        Repository.loadAllFriends(context,userId)
    }

    fun loadAllFriends(userId: Long){
        loadAllFriendsLiveData.value = userId
    }

}