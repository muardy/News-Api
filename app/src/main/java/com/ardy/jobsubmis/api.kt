package com.ardy.jobsubmis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardy.jobsubmis.connect.AdapterModelView
import com.ardy.jobsubmis.connect.MainViewModel
import com.ardy.jobsubmis.data.NewsData
import com.ardy.jobsubmis.databinding.ActivityApiBinding
import com.google.android.material.navigation.NavigationView


class api : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: AdapterModelView
    private lateinit var binding: ActivityApiBinding
    companion object{

        const val ARG_section_nol= "0"
        const val ARG_section_satu= "1"
        const val ARG_section_dua= "2"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if ( intent?.getParcelableExtra(ARG_section_nol) as? NewsData != null ) {
            showRecyclerList()
        }

        if ( intent?.getParcelableExtra(ARG_section_satu) as? NewsData != null ) {
            showRecyclerListCnn()
        }

        if ( intent?.getParcelableExtra(ARG_section_dua) as? NewsData != null ) {
            showRecyclerListSky()
        }

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
        supportActionBar?.title = "News List"
        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.nav_news)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var title = getString(R.string.app_name)
        when (item.itemId) {
            R.id.nav_news -> {
                val intent = Intent(this@api, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity((intent))

            }
            R.id.nav_crud -> {
                val intent = Intent(this@api , Journalist::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity((intent))

            }

        }

        supportActionBar?.title = title

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun showRecyclerList(){
        adapter = AdapterModelView()
        adapter.notifyDataSetChanged()
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter= adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getGith().observe(this, { Github ->
            if (Github != null) {
                adapter.setData(Github)
                binding.progressbar.visibility = View.INVISIBLE
            }
        })
        binding.progressbar.visibility = View.VISIBLE
        mainViewModel.setItems()
    }

    private fun showRecyclerListCnn(){
        adapter = AdapterModelView()
        adapter.notifyDataSetChanged()
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter= adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getGith().observe(this, { Github ->
            if (Github != null) {
                adapter.setData(Github)
                binding.progressbar.visibility = View.INVISIBLE
            }
        })
        binding.progressbar.visibility = View.VISIBLE
        mainViewModel.setItemsCnn()
    }

    private fun showRecyclerListSky(){
        adapter = AdapterModelView()
        adapter.notifyDataSetChanged()
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter= adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getGith().observe(this, { Github ->
            if (Github != null) {
                adapter.setData(Github)
                binding.progressbar.visibility = View.INVISIBLE
            }
        })
        binding.progressbar.visibility = View.VISIBLE
        mainViewModel.setItemsSky()
    }
}



