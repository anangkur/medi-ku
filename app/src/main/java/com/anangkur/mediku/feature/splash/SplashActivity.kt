package com.anangkur.mediku.feature.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.feature.auth.signIn.SignInActivity
import com.anangkur.mediku.feature.mens.menstrualEdit.MenstrualEditActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_splash.*

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

        observeViewModel()
        mViewModel.saveCountry(getUserCountry(this)?:"id")
        mViewModel.getProfile()
    }

    private fun openActivity(isLoggedIn: Boolean){
        val handler = Handler()
        handler.postDelayed({
            if (isLoggedIn){
                MenstrualEditActivity.startActivity(this)
            }else{
                SignInActivity.startActivity(this)
            }
            finish()
        }, 1000)
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetProfile.observe(this@SplashActivity, Observer {
                if (it){
                    pb_splash.visible()
                }else{
                    pb_splash.gone()
                }
            })
            successGetProfile.observe(this@SplashActivity, Observer {
                openActivity(it)
            })
            errorGetProfile.observe(this@SplashActivity, Observer {
                showToastShort(it)
                finish()
            })
        }
    }
}
