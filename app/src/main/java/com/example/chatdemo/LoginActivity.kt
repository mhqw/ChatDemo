package com.example.chatdemo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatdemo.utils.ActivityCollector
import com.example.chatdemo.utils.BaseActivity
import com.example.chatdemo.viewmodel.UserViewModel
import com.example.nettyserver.utils.LoginState
import com.example.nettyserver.utils.MD5
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.concurrent.thread

class LoginActivity : BaseActivity() {

    companion object{
        const val TAG = "LoginActivity"

        fun actionStart(context: Context){
            val intent = Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
        }

        fun actionStart(context: Context,newUserId: Long){
            val intent = Intent(context,LoginActivity::class.java).apply {
                putExtra("new_user_id",newUserId)
            }
            context.startActivity(intent)
        }
    }

    private val userViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        initView()

        initListener()

        initViewModelObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    private fun initActivity(){
        setContentView(R.layout.activity_login)
        //将当前activity 加入activity 管理器
        ActivityCollector.addActivity(this)
        //初始化userViewModel 工作
        userViewModel.context = this

        //如果本地有登录数据，直接进入主界面
        if(userViewModel.isUserSave()){
            val user = userViewModel.getUserData()
            MainActivity.actionStart(this,user)
            this.finish()
        }

        //隐藏状态栏
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.BLACK
    }

    private fun initView(){
        //如果刚刚注册成功，则自动填入刚刚注册成功的账号
        val newUserId = intent.getLongExtra("new_user_id",-1)
        if(newUserId != -1L){
            account.setText(newUserId.toString())
        }
    }

    private fun initListener(){
        login.setOnClickListener {
            //读取数据
            val userAccount = account.text.toString().toLong()
            userViewModel.checkUserInfo(userAccount)
        }

        register.setOnClickListener {
            RegisterActivity.actionStart(this)
        }
    }

    private fun initViewModelObserver(){
        userViewModel.checkUserListLiveData.observe(this, Observer { result ->
            val userList = result.getOrNull()
            if(userList != null){
                val password = password.text.toString()
                userViewModel.checkUserList.clear()
                userViewModel.checkUserList.addAll(userList)
                //userList.size = 1
                for(user in userList){
                    if(MD5.encode(password) != user.password){
                        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
                        return@Observer
                    }
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    MainActivity.actionStart(this,user)
                    userViewModel.saveUser(user)
                    return@Observer
                }
                //userList.size = 0
                Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show()
            }else{
                userViewModel.checkUserList.clear()
                Toast.makeText(this, "登录失败，请稍后再试", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}