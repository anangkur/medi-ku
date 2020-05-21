package com.anangkur.mediku.feature.view.dashboard.main.profile

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.databinding.FragmentProfileBinding
import com.anangkur.mediku.feature.view.about.AboutActivity
import com.anangkur.mediku.feature.view.auth.editPassword.EditPasswordActivity
import com.anangkur.mediku.feature.view.profile.editProfile.EditProfileActivity
import com.anangkur.mediku.feature.view.dashboard.main.MainActivity
import com.anangkur.mediku.feature.view.profile.userProfile.ProfileActionListener
import com.anangkur.mediku.feature.view.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*

class ProfileFragment: BaseFragment<FragmentProfileBinding, ProfileViewModel>(), ProfileActionListener {

    companion object{
        fun newInstance() = ProfileFragment()
    }

    override val mViewModel: ProfileViewModel
        get() = obtainViewModel(ProfileViewModel::class.java)

    override fun setupView(container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(LayoutInflater.from(requireContext()), container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()
        mLayout.btnEditProfile.setOnClickListener { this.onClickEditProfile() }
        mLayout.btnEditPassword.setOnClickListener { this.onClickEditPassword() }
        mLayout.btnAbout.setOnClickListener { this.onCLickAbout() }
        mLayout.btnLogout.setOnClickListener { this.onClickLogout() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
    }

    private fun setupToolbar(){
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(this@ProfileFragment.mLayout.toolbarProfile)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetProfile.observe(this@ProfileFragment, Observer {
                setupProgressProfile(it)
            })
            successGetProfile.observe(this@ProfileFragment, Observer {
                setupView(it)
            })
            errorGetProfile.observe(this@ProfileFragment, Observer {
                requireActivity().showSnackbarLong(it)
            })
            progressLogout.observe(this@ProfileFragment, Observer {
                setupProgressLogout(it)
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
        mLayout.layoutProfile.visible()
        mLayout.tvName.text = data.name
        mLayout.tvEmail.text = data.email
        mLayout.tvHeightWeight.text = "Height: ${data.height}cm | Weight: ${data.weight}kg"
        mLayout.ivProfile.setImageUrl(data.photo)
        mLayout.ivProfile.setOnClickListener { this.onClickImage(data.photo) }
        setupEditPassword(data.providerName)
    }

    private fun setupEditPassword(providerId: String){
        when (providerId){
            Const.PROVIDER_FIREBASE -> { }
            Const.PROVIDER_GOOGLE -> { mLayout.btnEditPassword.gone() }
            Const.PROVIDER_PASSWORD -> { mLayout.btnEditPassword.visible() }
        }
    }

    private fun setupProgressProfile(isLoading: Boolean){
        if (isLoading){
            mLayout.pbProfile.visible()
            mLayout.layoutProfile.invisible()
        }else{
            mLayout.pbProfile.gone()
            mLayout.layoutProfile.visible()
        }
    }

    private fun setupProgressLogout(isLoading: Boolean){
        if (isLoading){
            mLayout.pbBtnLogout.visible()
            mLayout.btnLogout.gone()
        }else{
            mLayout.pbBtnLogout.gone()
            mLayout.btnLogout.visible()
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

    override fun onCLickAbout() {
        AboutActivity.startActivity(requireContext())
    }

    override fun onClickImage(imageUrl: String) {
        requireContext().showPreviewImage(imageUrl)
    }
}