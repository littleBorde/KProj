package com.example.module_base.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.module_base.R
import com.gyf.immersionbar.ImmersionBar

/**
 * @author zhudd
 * @description:
 * @date 2020/9/24 14:35
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mTopBarView: View
    protected lateinit var mTopBarTitle: TextView
    protected lateinit var mBackImg: ImageView
    private lateinit var mllTopBarLeft: LinearLayout
    private lateinit var mllTopBarRight: LinearLayout
    //完成按钮
    protected lateinit var mFinish: TextView
    protected lateinit var mThreePointMore: ImageView
    /**
     * 返回按钮监听
     */
    private var mFinishListener: View.OnClickListener? = null
    private var mMoreListener: View.OnClickListener? = null

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        super.setContentView(R.layout.activity_base)

        setContentViews()
        initTitleBar()
        getData()
        initView()
        dealData()
        initListener()
    }

    private fun initTitleBar() {
        mTopBarView = findViewById(R.id.base_title)
        mTopBarTitle = findViewById(R.id.tv_title_name)
        mBackImg = findViewById(R.id.ll_back_img)
        mllTopBarLeft = findViewById(R.id.ll_top_bar_left)
        mllTopBarRight = findViewById(R.id.ll_top_bar_right)
        mFinish = findViewById(R.id.tv_base_finish)
        mThreePointMore = findViewById(R.id.iv_three_more)
        dealClick()
    }

    private fun dealClick() {
        mllTopBarLeft.setOnClickListener { finish() }
        mFinish.setOnClickListener { v ->
            {
                if (mFinishListener != null) {
                    mFinishListener!!.onClick(v)
                }
            }
        }
        mThreePointMore.setOnClickListener { v ->
            {
                if (mMoreListener != null) {
                    mMoreListener!!.onClick(v)
                }
            }
        }
    }

    /**
     * 点击完成的回调
     *
     * @param listener
     */
    protected fun setFinishListener(listener: View.OnClickListener) {
        mFinishListener = listener
    }

    protected fun setMoreListener(listener: View.OnClickListener) {
        mMoreListener = listener
    }

    open fun showTopBar(total: Int, left: Int, right: Int) {
        mTopBarView.visibility = total
        mllTopBarLeft.visibility = left
        mllTopBarRight.visibility = right
    }

    open fun showTopBar(total: Int) {
        showTopBar(total, total, total)
    }

    abstract fun setContentViews()

    abstract fun getData()

    abstract fun initView()

    abstract fun dealData()

    abstract fun initListener()

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }

    /**
     * 全屏
     */
    protected fun setFullScreen() {
        ImmersionBar.with(this).init()
    }
}