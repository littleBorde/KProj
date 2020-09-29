package com.example.module_log

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import org.greenrobot.eventbus.EventBus
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class GetLogService : IntentService("GetLogService") {

    private val TAG = "GetLogService"

    private lateinit var logContent: StringBuffer
    //筛选需要的字串
    private var strFilter = ""

    companion object {
        public var stop = false
    }

    override fun onHandleIntent(intent: Intent?) {
        logContent = StringBuffer()
        strFilter = intent!!.getStringExtra("filterStr")
        var pro: Process
        var bufferedReader: BufferedReader? = null
        try {
            val running = arrayOf("logcat", strFilter+":D", "*:S")
            pro = Runtime.getRuntime().exec(running)
            bufferedReader = BufferedReader(InputStreamReader(
                    pro.inputStream))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var line: String
        try {
            System.out.println(bufferedReader!!.readLine())
            while (!stop) {
                line = bufferedReader.readLine()
                if (line.indexOf(strFilter) >= 0) {
                    //读出每行log信息
                    println(line)
                    logContent.delete(0, logContent.length)
                    logContent.append(line)
                    logContent.append("\n")
                    //发送log内容
                    EventBus.getDefault().post(logContent.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
