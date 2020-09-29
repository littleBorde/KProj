package com.example.zhudd.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        object : Thread() {
            //创建子线程
            override fun run() {
                try {
                    Thread.sleep(1000)//使程序休眠一秒
                    val intent = Intent(this@SplashAct, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }.start()//启动线程
    }
}
