package com.example.nettyserver.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatdemo.logic.data.dao.GroupDao
import com.example.nettyserver.data.dao.UserDao
import com.example.nettyserver.data.entity.Friend
import com.example.nettyserver.data.entity.Group
import com.example.nettyserver.data.entity.GroupInfo
import com.example.nettyserver.data.entity.UserInfo

@Database(version = 1,entities =
    [UserInfo::class,Friend::class,GroupInfo::class,Group::class]
    ,exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    abstract fun groupDao(): GroupDao

    companion object{
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"app_database")
                .fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }
    }

}