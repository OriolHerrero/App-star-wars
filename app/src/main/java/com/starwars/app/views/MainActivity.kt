package com.starwars.app.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.starwars.app.R

class MainActivity : AppCompatActivity() {

    private val tabPlanets = 0
    private val tabFilms = 1

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tabbar: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        tabbar = findViewById(R.id.tabbar)

        setSupportActionBar(toolbar)
        setupTabbar()
    }

    private fun setupTabbar() {
        tabbar.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when(tab.id) {
                        tabFilms -> replaceFragment(FilmsFragment())
                        else -> replaceFragment(PlanetsFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })

        val tabP = tabbar.newTab().setText(R.string.tab_planets).setId(tabPlanets)
        tabbar.addTab(tabP)
        val tabF = tabbar.newTab().setText(R.string.tab_films).setId(tabFilms)
        tabbar.addTab(tabF)

        tabbar.selectTab(tabP)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}