package xyz.savvamirzoyan.nous

import android.os.Bundle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import xyz.savvamirzoyan.nous.databinding.ActivityMainBinding
import xyz.savvamirzoyan.nous.shared_app.CoreActivity

@AndroidEntryPoint
class MainActivity : CoreActivity() {

    private lateinit var binding: ActivityMainBinding

    // useless bc no activity-scope navigation presented like bottom navigation or navigation drawer
    @Suppress("unused")
    private val navController by lazy { findNavController(R.id.fragment_mainNavHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun loading(state: Boolean) {
        when (state) {
            true -> binding.progressBar.show()
            false -> binding.progressBar.hide()
        }
    }
}