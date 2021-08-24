package com.michlindev.dariointerview.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.michlindev.dariointerview.R
import com.michlindev.dariointerview.viewmodel.SharedViewModel
import com.michlindev.dariointerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    /*TODO
    1. Beautify
    2. Add folders - V
    3. Bar name - V
    4. Constants
    5. Comment code
    6. Make app icon
    7. Clean code
    8. XML Strings
    */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        title = "Search"
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
                viewModel.setMenu(SharedViewModel.Companion.Selection.SEARCH)
                title = "Search"
                true
            }
            R.id.menu_favorites -> {
                viewModel.setMenu(SharedViewModel.Companion.Selection.FAVORITES)
                title = "Favorites"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}