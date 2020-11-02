package com.example.zhudd.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.module_base.utils.KLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTestCrash.setOnClickListener {
            KLog.d()
            var a = 10/0
        }
    }
}
