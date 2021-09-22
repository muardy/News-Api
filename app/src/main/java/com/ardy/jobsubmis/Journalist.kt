package com.ardy.jobsubmis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardy.jobsubmis.adapter.JourAdapter
import com.ardy.jobsubmis.databinding.ActivityJournalistBinding
import com.ardy.jobsubmis.db.JourHelper
import com.ardy.jobsubmis.entity.jour
import com.ardy.jobsubmis.helper.MappingHelper
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Journalist : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var adapter: JourAdapter

    private lateinit var binding: ActivityJournalistBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Journalist Data"

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)
        adapter = JourAdapter(this)
        binding.rvNotes.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@Journalist, crud::class.java)
            startActivityForResult(intent, crud.REQUEST_ADD)
        }

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {

            val list = savedInstanceState.getParcelableArrayList<jour>(EXTRA_STATE)
            showSnackbarMessage(list.toString())
            if (list != null) {
                adapter.listNotes = list
                showSnackbarMessage(list.toString())
            }
            showSnackbarMessage(list.toString())
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
        supportActionBar?.title = getString(R.string.jour)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.nav_crud)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var title = getString(R.string.app_name)
        when (item.itemId) {
            R.id.nav_news -> {
                val intent = Intent(this@Journalist , MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity((intent))

            }
            R.id.nav_crud -> {
                val intent = Intent(this@Journalist , Journalist::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity((intent))

            }

        }
//        if (intent!= null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, intent)
//                .commit()
//        }
        supportActionBar?.title = title

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressbar.visibility = View.VISIBLE
            val noteHelper = JourHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)

            }
            val cursor = noteHelper.queryAll()
            MappingHelper.mapCursorToArrayList(cursor)

//            noteHelper.close()

            val notes = deferredNotes.await()
            if (notes.size > 0) {

                adapter.listNotes = notes
            } else {

                adapter.listNotes = ArrayList()
                showSnackbarMessage("No data at this time")
            }
            for(i in 0 until notes.size){
                Log.d(TAG, notes.get(i).title.toString())
            }
            noteHelper.close()
            binding.progressbar.visibility = View.INVISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            when (requestCode) {
                crud.REQUEST_ADD -> if (resultCode == crud.RESULT_ADD) {
                    val note = data.getParcelableExtra<jour>(crud.EXTRA_NOTE) as jour

                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)

                    showSnackbarMessage("One data added successfully")
                }
                crud.REQUEST_UPDATE ->
                    when (resultCode) {
                        crud.RESULT_UPDATE -> {

                            val note = data.getParcelableExtra<jour>(crud.EXTRA_NOTE) as jour
                            val position = data.getIntExtra(crud.EXTRA_POSITION, 0)

                            adapter.updateItem(position, note)
                            binding.rvNotes.smoothScrollToPosition(position)

                            showSnackbarMessage("One data changed successfully")
                        }

                        crud.RESULT_DELETE -> {
                            val position = data.getIntExtra(crud.EXTRA_POSITION, 0)

                            adapter.removeItem(position)

                            showSnackbarMessage("One data deleted successfully")
                        }
                    }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }
}