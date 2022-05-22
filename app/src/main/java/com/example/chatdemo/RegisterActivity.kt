package com.example.chatdemo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatdemo.utils.ActivityCollector
import com.example.chatdemo.utils.BaseActivity
import com.example.chatdemo.viewmodel.UserViewModel
import com.example.nettyserver.data.entity.UserInfo
import com.example.nettyserver.utils.MD5
import com.example.nettyserver.utils.RegisterState
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.register
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    companion object{
        val TAG = "RegisterActivity"

        fun actionStart(context: Context){
            val intent = Intent(context,RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val userViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        initListener()

        initViewModelObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    private fun initActivity(){
        setContentView(R.layout.activity_register)
        //将当前activity 加入activity 管理器
        ActivityCollector.addActivity(this)
        //初始化userViewModel 工作
        userViewModel.context = this

        //隐藏状态栏
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.BLACK
    }

    private fun initListener() {
        register.setOnClickListener {
            //读取数据
            val newEmail = email.text.toString()
            val newPassword = password.text.toString()
            val newConfirmPassword = confirm_password.text.toString()
            val newName = name.text.toString()
            var flag = 0

            //判断两次密码是否相同
            if(newPassword != newConfirmPassword){
                flag = RegisterState.REGISTER_CONFIRM_PASSWORD_ERROR
                Log.e(TAG,"RegisterState.REGISTER_CONFIRM_PASSWORD_ERROR")
                Toast.makeText(this, "二次密码不同", Toast.LENGTH_SHORT).show()
            }else{
                //插入数据
                val newUserInfo = UserInfo(newEmail,MD5.encode(newPassword),newName)
                userViewModel.insertUser(newUserInfo)
            }
        }
    }

    private fun initViewModelObserver(){
        userViewModel.newUserIdLiveData.observe(this, Observer { result->
            val newUserId = result.getOrNull()
            if(newUserId != null){
                userViewModel.newUserId = newUserId
                LoginActivity.actionStart(this,newUserId)
            }else{
                userViewModel.newUserId = -1
                Toast.makeText(this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}