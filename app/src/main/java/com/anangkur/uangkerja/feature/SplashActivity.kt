package com.anangkur.uangkerja.feature

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseActivity

class SplashActivity: BaseActivity<ViewModel>() {
    override val mLayout: Int
        get() = R.layout.activity_splash
    override val mViewModel: ViewModel?
        get() = null
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openActivity(3000)
    }

    private fun openActivity(delayTime: Long){
        val handler = Handler()
        handler.postDelayed({

            finish()
        }, delayTime)
    }
}
