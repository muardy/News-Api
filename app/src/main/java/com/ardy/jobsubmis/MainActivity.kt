package com.ardy.jobsubmis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardy.jobsubmis.data.AdapterSource
import com.ardy.jobsubmis.data.Data
import com.ardy.jobsubmis.data.NewsData
import com.ardy.jobsubmis.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityMainBinding
    private var list: ArrayList<NewsData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list.addAll(Data.listData)
        showRecyclerList()

        setSupportActionBar(binding.appBarMain.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.title = getString(R.string.source)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.nav_news)


    }


    private fun showRecyclerList() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = AdapterSource(list)
        binding.rvNews.adapter = listHeroAdapter
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var title = getString(R.string.app_name)
//        setbac(ContextCompat.getColor(context, R.color.colorPrimaryDark))

        when (item.itemId) {
            R.id.nav_news -> {
                AppCompatResources.getColorStateList(this, R.color.blue_700)
                val intent = Intent(this@MainActivity , MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity((intent))

            }
            R.id.nav_crud -> {
                val intent = Intent(this@MainActivity , Journalist::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity((intent))

            }

        }

        supportActionBar?.title = title

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}