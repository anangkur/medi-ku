package com.anangkur.mediku.feature.auth.signIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.feature.auth.forgotPassword.ForgotPasswordActivity
import com.anangkur.mediku.feature.dashboard.main.MainActivity
import com.anangkur.mediku.feature.auth.signUp.SignUpActivity
import com.anangkur.mediku.util.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity: BaseActivity<SignInViewModel>(), SignInActionListener {

    companion object{
        const val RC_SIGN_IN = 9001
        fun startActivity(context: Context){
            context.startActivity(Intent(context, SignInActivity::class.java))
        }
        fun startActivityClearStack(context: Context){
            context.startActivity(Intent(context, SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_sign_in
    override val mViewModel: SignInViewModel
        get() = obtainViewModel(SignInViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupGoogleSignIn()
        setupView()
        setupTextWatcher()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                mViewModel.firebaseSignInWithGoogle(account)
            } catch (e: ApiException) {
                showSnackbarLong(e.message?:"")
            }
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressSignInLive.observe(this@SignInActivity, Observer {
                if (it){
                    btn_signin.showProgress()
                }else{
                    btn_signin.hideProgress()
                }
            })
            resultSignInLive.observe(this@SignInActivity, Observer {
                MainActivity.startActivityClearStack(this@SignInActivity)
            })
            successCreateUser.observe(this@SignInActivity, Observer {
                MainActivity.startActivityClearStack(this@SignInActivity)
            })
            errorSignInLive.observe(this@SignInActivity, Observer {
                showSnackbarLong(it)
            })
            progressSignInGoogleLive.observe(this@SignInActivity, Observer {
                if (it){
                    pb_btn_signin_google.visible()
                    btn_signin_google.gone()
                }else{
                    pb_btn_signin_google.gone()
                    btn_signin_google.visible()
                }
            })
        }
    }

    private fun setupView(){
        btn_signin.setOnClickListener { this.onClickSignIn(et_email.text.toString(), et_password.text.toString()) }
        btn_forgot_password.setOnClickListener { this.onClickForgot() }
        btn_register.setOnClickListener { this.onClickSignUp() }
        btn_signin_google.setOnClickListener { this.onClickGoogle() }
    }

    override fun onClickSignIn(email: String?, password: String?) {
        validateInput(email, password)
    }

    override fun onClickForgot() {
        ForgotPasswordActivity.startActivity(this)
    }

    override fun onClickSignUp() {
        SignUpActivity.startActivity(this)
    }

    override fun onClickGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun validateInput(email: String?, password: String?){
        when {
            email.isNullOrEmpty() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_empty)
            }
            !email.validateEmail() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_not_valid)
            }
            password.isNullOrEmpty() -> {
                til_password.isErrorEnabled = true
                til_password.error = getString(R.string.error_password_empty)
            }
            !password.validatePassword() -> {
                til_password.isErrorEnabled = true
                til_password.error = getString(R.string.error_password_not_valid)
            }
            else -> {
                til_email.isErrorEnabled = false
                til_password.isErrorEnabled = false
                mViewModel.firebaseSignIn(email, password)
            }
        }
    }

    private fun setupTextWatcher(){
        et_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validateEmail() -> {
                        til_email.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_password.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePassword() -> {
                        til_password.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupGoogleSignIn(){
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}
