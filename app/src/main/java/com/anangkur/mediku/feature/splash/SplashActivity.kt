package com.anangkur.mediku.feature.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.feature.auth.signIn.SignInActivity
import com.anangkur.mediku.feature.dashboard.main.MainActivity
import com.anangkur.mediku.util.*
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
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
        getTokenFirebase()
        mViewModel.checkLogin()
    }

    private fun openActivity(isLoggedIn: Boolean){
        val handler = Handler()
        handler.postDelayed({
            if (isLoggedIn){
                MainActivity.startActivity(this)
            }else{
                SignInActivity.startActivity(this)
            }
            finish()
        }, 1000)
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetProfile.observe(this@SplashActivity, Observer {
                setupLoading(it)
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

    private fun setupLoading(isLoading: Boolean){
        if (isLoading){
            pb_splash.visible()
        }else{
            pb_splash.gone()
        }
    }

    private fun getTokenFirebase(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task: Task<InstanceIdResult?> ->
                if (!task.isSuccessful) {
                    Log.w("SplashScreenActivity", "getInstanceId failed", task.exception)
                    return@addOnCompleteListener
                }else{
                    val token = task.result?.token
                    Log.d("SplashScreenActivity", token?:"empty token")
                    mViewModel.saveFirebaseToken(token?:"empty token")
                }
            }
    }
}
