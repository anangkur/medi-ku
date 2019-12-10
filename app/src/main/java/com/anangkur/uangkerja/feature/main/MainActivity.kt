package com.anangkur.uangkerja.feature.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.feature.main.history.HistoryFragment
import com.anangkur.uangkerja.feature.main.home.HomeFragment
import com.anangkur.uangkerja.feature.main.pocket.PocketFragment
import com.anangkur.uangkerja.feature.main.profile.ProfileFragment
import com.anangkur.uangkerja.feature.main.topup.TopupFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNav()
        loadFragment(HomeFragment.newInstance())
    }

    private fun setupBottomNav(){
        bottom_nav_main.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_history -> {
                    loadFragment(HistoryFragment.newInstance())
                    true
                }
                R.id.menu_topup -> {
                    loadFragment(TopupFragment.newInstance())
                    true
                }
                R.id.menu_home -> {
                    loadFragment(HomeFragment.newInstance())
                    true
                }
                R.id.menu_profile -> {
                    loadFragment(ProfileFragment.newInstance())
                    true
                }
                R.id.menu_pocket -> {
                    loadFragment(PocketFragment.newInstance())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
