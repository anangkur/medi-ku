package com.anangkur.mediku.feature.view.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.databinding.ActivitySplashBinding
import com.anangkur.mediku.feature.view.auth.signIn.SignInActivity
import com.anangkur.mediku.feature.view.dashboard.main.MainActivity
import com.anangkur.mediku.util.*
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

class SplashActivity: BaseActivity<ActivitySplashBinding, SplashViewModel>() {

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

    override fun setupView(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
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
            mLayout.pbSplash.visible()
        }else{
            mLayout.pbSplash.gone()
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
