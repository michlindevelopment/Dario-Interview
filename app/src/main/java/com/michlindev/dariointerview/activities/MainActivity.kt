package com.michlindev.dariointerview.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.michlindev.dariointerview.R
import com.michlindev.dariointerview.databinding.ActivityMainBinding
import com.michlindev.dariointerview.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    /*TODO
    1. Beautify - V
    2. Add folders - V
    3. Bar name - V
    4. Constants - V
    5. Document code - V
    6. Make app icon - V
    7. Clean code
    8. XML Strings - V
    9. Open Imdb - V
    */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        title = getString(R.string.search)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection, set current state
        return when (item.itemId) {
            R.id.menu_search -> {
                viewModel.setMenu(SharedViewModel.Companion.Selection.SEARCH)
                title = getString(R.string.search)
                true
            }
            R.id.menu_favorites -> {
                viewModel.setMenu(SharedViewModel.Companion.Selection.FAVORITES)
                title = getString(R.string.favorites)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}