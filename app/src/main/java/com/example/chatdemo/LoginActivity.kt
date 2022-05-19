package com.example.chatdemo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nettyserver.data.database.AppDatabase
import com.example.nettyserver.data.entity.UserInfo
import com.example.nettyserver.utils.LoginState
import com.example.nettyserver.utils.MD5
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    companion object{
        const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //隐藏状态栏
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        initView()

        initListener()
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
            val userPassword = password.text.toString()
            val userDao = AppDatabase.getDatabase(this).userDao()
            var flag = 0
            var userInfo: UserInfo? = null
            thread {
                //查询数据
                for(user in userDao.checkUserInfo(userAccount)){
                    userInfo = user
                    if(userInfo == null){
                        flag = LoginState.USERINFO_USERID_ERROR
                    }else if(MD5.encode(userPassword) != userInfo!!.password){
                        flag = LoginState.USERINFO_PASSWORD_ERROR
                    }
                    flag = LoginState.USERINFO_SUCCESS
                }
                //FIXME:LiveData 回调改造
                when(flag){
                    LoginState.USERINFO_SUCCESS -> {
                        val intent = Intent(this,MainActivity::class.java)
                        intent.putExtra("user_data",userInfo)
                        startActivity(intent)
                    }
                    else ->{
                        Toast.makeText(this, "账号或密码错误 flag: $flag", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}