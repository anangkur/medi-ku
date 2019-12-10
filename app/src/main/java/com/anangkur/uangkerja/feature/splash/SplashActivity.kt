package com.anangkur.uangkerja.feature.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseActivity
import com.anangkur.uangkerja.feature.login.LoginActivity
import com.anangkur.uangkerja.feature.main.MainActivity
import com.anangkur.uangkerja.util.obtainViewModel

class SplashActivity: BaseActivity<SplashViewModel>() {
    override val mLayout: Int
        get() = R.layout.activity_splash
    override val mViewModel: SplashViewModel
        get() = obtainViewModel(SplashViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openActivity()
    }

    private fun openActivity(){
        val handler = Handler()
        handler.postDelayed({
            if (mViewModel.isLoggedIn()){
                MainActivity.startActivity(this)
            }else{
                LoginActivity.startActivity(this)
            }
            finish()
        }, 3000)
    }
}
