package com.anangkur.mediku.feature.main.profile

import android.os.Bundle
import android.view.*
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.feature.main.MainActivity
import com.anangkur.mediku.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: BaseFragment<ProfileViewModel>() {

    companion object{
        fun newInstance() = ProfileFragment()
    }

    override val mLayout: Int
        get() = R.layout.fragment_profile
    override val mViewModel: ProfileViewModel?
        get() = obtainViewModel(ProfileViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
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
}