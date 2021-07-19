package com.anangkur.mediku.feature.view.auth.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.databinding.ActivitySignUpBinding
import com.anangkur.mediku.feature.view.dashboard.main.MainActivity
import com.anangkur.mediku.feature.view.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class SignUpActivity: BaseActivity<ActivitySignUpBinding, SignUpViewModel>(), SignUpActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }

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
        setupView(mLayout)
        setuptextWatcher()
        observeViewModel()
    }

    override fun setupView(): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
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
                setupLoadingSignup(it)
            })
            successCreateUser.observe(this@SignUpActivity, Observer {
                MainActivity.startActivityClearStack(this@SignUpActivity)
            })
            errorSignUpLive.observe(this@SignUpActivity, Observer {
                showSnackbarLong(it)
            })
            progressSignUpGoogleLive.observe(this@SignUpActivity, Observer {
                setupLoadingSignupGoogle(it)
            })
        }
    }

    private fun setupView(mLayout: ActivitySignUpBinding){
        mLayout.btnSignin.setOnClickListener { this.onClickSignIn() }
        mLayout.btnSigninGoogle.setOnClickListener { this.onClickSignUpGoogle() }
        mLayout.btnSignup.setOnClickListener { this.onClickSignUp(
            name = mLayout.etName.text.toString(),
            confirmPassword = mLayout.etPasswordConfirm.text.toString(),
            email = mLayout.etEmail.text.toString(),
            password = mLayout.etPassword.text.toString()) }
    }

    private fun setupGoogleSignIn(){
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupLoadingSignupGoogle(isLoading: Boolean){
        if (isLoading){
            mLayout.pbBtnSignupGoogle.visible()
            mLayout.btnSigninGoogle.gone()
        }else{
            mLayout.pbBtnSignupGoogle.gone()
            mLayout.btnSigninGoogle.visible()
        }
    }

    private fun setupLoadingSignup(isLoading: Boolean){
        if (isLoading){
            mLayout.btnSignup.showProgress()
        }else{
            mLayout.btnSignup.hideProgress()
        }
    }

    override fun onClickSignUp(
        name: String?,
        email: String?,
        password: String?,
        confirmPassword: String?
    ) {
        when {
            name.isNullOrEmpty() -> {
                mLayout.tilName.isErrorEnabled = true
                mLayout.tilName.error = getString(R.string.error_name_empty)
            }
            email.isNullOrEmpty() -> {
                mLayout.tilEmail.isErrorEnabled = true
                mLayout.tilEmail.error = getString(R.string.error_email_empty)
            }
            !email.validateEmail() -> {
                mLayout.tilEmail.isErrorEnabled = false
                mLayout.tilEmail.error = getString(R.string.error_email_not_valid)
            }
            password.isNullOrEmpty() -> {
                mLayout.tilPassword.isErrorEnabled = true
                mLayout.tilPassword.error = getString(R.string.error_password_empty)
            }
            !password.validatePassword() -> {
                mLayout.tilPassword.isErrorEnabled = true
                mLayout.tilPassword.error = getString(R.string.error_password_not_valid)
            }
            confirmPassword.isNullOrEmpty() -> {
                mLayout.tilPasswordConfirm.isErrorEnabled = true
                mLayout.tilPasswordConfirm.error = getString(R.string.error_confirm_password_empty)
            }
            !confirmPassword.validatePasswordConfirm(password) -> {
                mLayout.tilPasswordConfirm.isErrorEnabled = true
                mLayout.tilPasswordConfirm.error = getString(R.string.error_password_confirm_not_valid)
            }
            else -> {
                mViewModel.firebaseSignUp(name, email, password)
            }
        }
    }

    private fun setuptextWatcher(){
        mLayout.etName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    mLayout.tilName.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() || s.toString().validateEmail()){
                    mLayout.tilEmail.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() || s.toString().validatePassword()){
                    mLayout.tilPassword.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etPasswordConfirm.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() || s.toString().validatePasswordConfirm(mLayout.etPassword.text.toString())){
                    mLayout.tilPasswordConfirm.isErrorEnabled = false
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
