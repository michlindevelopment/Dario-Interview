package com.michlindev.dariointerview

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.michlindev.dariointerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
      /*  super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)*/

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_search -> {
                //newGame()
                viewModel.setMenu(SharedViewModel.Companion.Selection.SEARCH)

                Log.d("DTAG","menu_search")
                true
            }
            R.id.menu_favorites -> {
                //showHelp()
                Log.d("DTAG","menu_favorites")
                viewModel.setMenu(SharedViewModel.Companion.Selection.FAVORITES)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}