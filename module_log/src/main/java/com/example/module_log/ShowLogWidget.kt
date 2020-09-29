package com.example.module_log

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * @author zhudd
 * @description:
 * @date 2020/9/18 11:14
 */
class ShowLogWidget : RelativeLayout {

    private val TAG = "ShowLogWidget"

    private lateinit var tvShowLog: TextView
    private lateinit var rlContainer: RelativeLayout
    private lateinit var tvLog: EditText
    private lateinit var tvClearLog: TextView
    private lateinit var tvHideLayout: TextView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        val rootView = LayoutInflater.from(context).inflate(R.layout.layout_show_log, this)
        tvShowLog = rootView.findViewById(R.id.tvShowLog) as TextView
        rlContainer = rootView.findViewById(R.id.rlContainer) as RelativeLayout
        tvLog = rootView.findViewById(R.id.tvLog) as EditText
        tvClearLog = rootView.findViewById(R.id.tvClearLog) as TextView
        tvHideLayout = rootView.findViewById(R.id.tvHideLayout) as TextView

        tvShowLog.setOnClickListener {
            rlContainer.visibility = View.VISIBLE
            tvShowLog.visibility = View.GONE
            getSysLog()
        }

        tvHideLayout.setOnClickListener {
            rlContainer.visibility = View.GONE
            tvShowLog.visibility = View.VISIBLE
            GetLogService.stop = true
        }

        tvClearLog.setOnClickListener {
            tvLog.setText("")
        }
    }

    /**
     * 获取系统Log
     */
    private fun getSysLog() {
        var intent = Intent(context, GetLogService::class.java)
        intent.putExtra("filterStr", "zhdd")
        context.startService(intent)
    }

    /**
     * 显示Log
     */
    fun showLog(logMsg : String) {
        tvLog.setText(tvLog.text.toString() + logMsg)
    }
}