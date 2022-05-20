package com.example.chatdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatdemo.utils.ActivityCollector
import com.example.chatdemo.utils.BaseActivity

class MainActivity : BaseActivity() {

    companion object{
        const val TAG = "MainActivity"
        fun actionStart(context: Context){
            val intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.finishAll()
    }

}