package edu.rosehulman.historicaldocs

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DocListFragment.OnDocSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) { // doing rotation
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.fragment_container, AboutFragment(), "about")
            ft.commit()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Just in case we wanted to add the app bar later.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var switchTo: Fragment? = null
        when (item.itemId) {
            R.id.nav_about -> {
                // TODO: Launch the AboutFragment
                switchTo = AboutFragment()

            }
            R.id.nav_docs -> {
                // TODO: Launch a DocListFragment
                // (which later will launch DocDetailFragments)
                switchTo = DocListFragment()

            }
            R.id.nav_settings -> startActivity(Intent(Settings.ACTION_SETTINGS))
        }
        if (switchTo != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, switchTo, "about")
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
            ft.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDocSelected(doc: Doc) {
        Log.d(Constants.TAG, "Doc Selected:" + doc.toString())
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, DocDetailFragment.newInstance(doc), "detail")
        ft.addToBackStack("list")
        ft.commit()
    }
}
