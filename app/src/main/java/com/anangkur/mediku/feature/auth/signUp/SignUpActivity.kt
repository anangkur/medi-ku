package com.anangkur.mediku.feature.auth.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.feature.dashboard.main.MainActivity
import com.anangkur.mediku.feature.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity: BaseActivity<SignUpViewModel>(), SignUpActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_sign_up
    override val mViewModel: SignUpViewModel
        get() = obtainViewModel(SignUpViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupGoogleSignIn()
        setupView()
        setuptextWatcher()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SignInActivity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                mViewModel.firebaseSignUpWithGoogle(account)
            } catch (e: ApiException) {
                showSnackbarLong(e.message?:"")
            }
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressSignUpLive.observe(this@SignUpActivity, Observer {
                if (it){
                    btn_signup.showProgress()
                }else{
                    btn_signup.hideProgress()
                }
            })
            successCreateUser.observe(this@SignUpActivity, Observer {
                MainActivity.startActivityClearStack(this@SignUpActivity)
            })
            errorSignUpLive.observe(this@SignUpActivity, Observer {
                showSnackbarLong(it)
            })
            progressSignUpGoogleLive.observe(this@SignUpActivity, Observer {
                if (it){
                    pb_btn_signup_google.visible()
                    btn_signin_google.gone()
                }else{
                    pb_btn_signup_google.gone()
                    btn_signin_google.visible()
                }
            })
        }
    }

    private fun setupView(){
        btn_signin.setOnClickListener { this.onClickSignIn() }
        btn_signin_google.setOnClickListener { this.onClickSignUpGoogle() }
        btn_signup.setOnClickListener { this.onClickSignUp(
            name = et_name.text.toString(),
            confirmPassword = et_password_confirm.text.toString(),
            email = et_email.text.toString(),
            password = et_password.text.toString()) }
    }

    private fun setupGoogleSignIn(){
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onClickSignUp(
        name: String?,
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        when {
            name.isNullOrEmpty() -> {
                til_name.isErrorEnabled = true
                til_name.error = getString(R.string.error_name_empty)
            }
            email.isNullOrEmpty() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_empty)
            }
            !email.validateEmail() -> {
                til_email.isErrorEnabled = false
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
            confirmPassword.isNullOrEmpty() -> {
                til_password_confirm.isErrorEnabled = true
                til_password_confirm.error = getString(R.string.error_confirm_password_empty)
            }
            !confirmPassword.validatePasswordConfirm(password) -> {
                til_password_confirm.isErrorEnabled = true
                til_password_confirm.error = getString(R.string.error_password_confirm_not_valid)
            }
            else -> {
                mViewModel.firebaseSignUp(name, email, password)
            }
        }
    }

    private fun setuptextWatcher(){
        et_name.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    til_name.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() || s.toString().validateEmail()){
                    til_email.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_password.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() || s.toString().validatePassword()){
                    til_password.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_password_confirm.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() || s.toString().validatePasswordConfirm(et_password.text.toString())){
                    til_password_confirm.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onClickSignIn() {
        SignInActivity.startActivity(this)
    }

    override fun onClickSignUpGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, SignInActivity.RC_SIGN_IN)
    }
}
