package com.example.chatdemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatdemo.R
import com.example.nettyserver.data.entity.UserInfo

class FriendAdapter(val friendList: List<UserInfo>) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val friendIcon = view.findViewById<ImageView>(R.id.friend_icon)
        val friendName = view.findViewById<TextView>(R.id.friend_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendList[position]
        holder.friendName.text = friend.name
    }

    override fun getItemCount() = friendList.size
}