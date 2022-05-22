package com.example.chatdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatdemo.ui.FriendAdapter
import com.example.chatdemo.utils.ActivityCollector
import com.example.chatdemo.utils.BaseActivity
import com.example.chatdemo.viewmodel.FriendViewModel
import com.example.chatdemo.viewmodel.UserViewModel
import com.example.nettyserver.data.entity.UserInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object{
        const val TAG = "MainActivity"
        fun actionStart(context: Context,user: UserInfo){
            val intent = Intent(context,MainActivity::class.java).apply {
                putExtra("user_data",user)
            }
            context.startActivity(intent)
        }
    }

    private val friendAdapter by lazy {
        FriendAdapter(friendViewModel.allFriendsList)
    }

    private val friendViewModel by lazy {
        ViewModelProvider(this).get(FriendViewModel::class.java)
    }
    private val userViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private lateinit var user: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        initView()

        initViewModelObserver()

        loadAllFriends()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.finishAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.add_friend -> {
                Toast.makeText(this, "添加好友", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun initActivity(){
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        friendViewModel.context = this

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        window.statusBarColor = Color.BLACK
        ActivityCollector.addActivity(this)
        onNavigationItemSelected()

        if(userViewModel.isUserSave()) {
            Log.e(TAG,"UserIsSave")
            //获取用户
            user = intent.getParcelableExtra<UserInfo>("user_data") as UserInfo
        }
    }

    private fun onNavigationItemSelected(){
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navCall -> {
                    Toast.makeText(this, "navCall", Toast.LENGTH_SHORT).show()
                }
                R.id.navFriends -> {
                    Toast.makeText(this, "navFriends", Toast.LENGTH_SHORT).show()
                }
                R.id.navQuit -> {
                    userViewModel.clearUserData()
                    LoginActivity.actionStart(this)
                }
            }
            //处理完毕返回true
            true
        }
    }

    private fun initView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = friendAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModelObserver(){
        friendViewModel.allFriendsLiveData.observe(this, Observer { result ->
            //TODO:显示好友名称，头像
            val friendList = result.getOrNull()
            if(friendList != null){
                //获得成功
                friendViewModel.allFriendsList.clear()
                friendViewModel.allFriendsList.addAll(friendList)
                Toast.makeText(this, "list.size: ${friendList.size}", Toast.LENGTH_SHORT).show()
                runOnUiThread {
                    friendAdapter.notifyDataSetChanged()
                }
            }else{
                friendViewModel.allFriendsList.clear()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    private fun loadAllFriends(){
        friendViewModel.loadAllFriends(user.userId)
    }

}