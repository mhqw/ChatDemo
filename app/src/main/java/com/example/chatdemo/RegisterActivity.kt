package com.example.chatdemo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.nettyserver.data.database.AppDatabase
import com.example.nettyserver.data.entity.UserInfo
import com.example.nettyserver.utils.MD5
import com.example.nettyserver.utils.RegisterState
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.register
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {

    companion object{
        val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //隐藏状态栏
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        initListener()
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
                val userDao = AppDatabase.getDatabase(this).userDao()
                val newUserInfo = UserInfo(newEmail,MD5.encode(newPassword),newName)
                thread {
                    newUserInfo.user_id = userDao.insertUser(newUserInfo)
                }

                //FIXME:利用 LiveData 来改造进行回调
                while(newUserInfo.user_id == 0L){
                    Thread.sleep(100)
                }
                flag = RegisterState.REGISTER_CREAT_SUCCESS
                Log.e(TAG,"RegisterState.REGISTER_CREAT_SUCCESS")
                Log.e(TAG,"newUserId = ${newUserInfo.user_id}")
                val intent = Intent(this,LoginActivity::class.java)
                intent.putExtra("new_user_id",newUserInfo.user_id)
                startActivity(intent)
            }
        }
    }
}