package com.anangkur.mediku.feature.dashboard.main.profile

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.feature.auth.editPassword.EditPasswordActivity
import com.anangkur.mediku.feature.editProfile.EditProfileActivity
import com.anangkur.mediku.feature.dashboard.main.MainActivity
import com.anangkur.mediku.feature.profile.ProfileActionListener
import com.anangkur.mediku.feature.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: BaseFragment<ProfileViewModel>(), ProfileActionListener {

    companion object{
        fun newInstance() = ProfileFragment()
    }

    override val mLayout: Int
        get() = R.layout.fragment_profile
    override val mViewModel: ProfileViewModel
        get() = obtainViewModel(ProfileViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()
        btn_edit_profile.setOnClickListener { this.onClickEditProfile() }
        btn_edit_password.setOnClickListener { this.onClickEditPassword() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
    }

    private fun setupToolbar(){
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(toolbar_profile)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_logout -> {
                this.onClickLogout()
                true
            }
            else -> false
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetProfile.observe(this@ProfileFragment, Observer {
                if (it){
                    pb_profile.visible()
                    layout_profile.invisible()
                }else{
                    pb_profile.gone()
                    layout_profile.visible()
                }
            })
            successGetProfile.observe(this@ProfileFragment, Observer {
                layout_profile.visible()
                setupView(it)
            })
            errorGetProfile.observe(this@ProfileFragment, Observer {
                requireActivity().showSnackbarLong(it)
            })
            progressLogout.observe(this@ProfileFragment, Observer {

            })
            successLogout.observe(this@ProfileFragment, Observer {
                SignInActivity.startActivityClearStack(requireContext())
            })
            errorLogout.observe(this@ProfileFragment, Observer {
                requireActivity().showSnackbarLong(it)
            })
        }
    }

    private fun setupView(data: User){
        tv_name.text = data.name
        tv_email.text = data.email
        tv_height_weight.text = "Height: ${data.height}cm | Weight: ${data.weight}kg"
        iv_profile.setImageUrl(data.photo)
        iv_profile.setOnClickListener { this.onClickImage(data.photo) }
        setupEditPassword(data.providerName)
    }

    private fun setupEditPassword(providerId: String){
        when (providerId){
            Const.PROVIDER_FIREBASE -> { }
            Const.PROVIDER_GOOGLE -> { btn_edit_password.gone() }
            Const.PROVIDER_PASSWORD -> { btn_edit_password.visible() }
        }
    }

    override fun onClickEditProfile() {
        EditProfileActivity.startActivity(requireContext())
    }

    override fun onClickEditPassword() {
        EditPasswordActivity.startActivity(requireContext())
    }

    override fun onClickLogout() {
        mViewModel.logout()
    }

    override fun onClickImage(imageUrl: String) {
        requireContext().showPreviewImage(imageUrl)
    }
}