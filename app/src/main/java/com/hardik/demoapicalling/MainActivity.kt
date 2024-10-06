package com.hardik.demoapicalling

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hardik.demoapicalling.databinding.ActivityMainBinding
import com.hardik.demoapicalling.domain.model.UserModel
import com.hardik.demoapicalling.domain.use_case.GetUserUseCase
import com.hardik.demoapicalling.presentation.MainViewModel
import com.hardik.demoapicalling.presentation.viewModelFactory

class MainActivity : AppCompatActivity() {
    private final val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainViewModel = ViewModelProvider(
            this,
            viewModelFactory { MainViewModel(GetUserUseCase(MyApp.appModule.userRepository)) }
        ).get(MainViewModel::class.java)

        mainViewModel.state.observe(this){
            it?.let {
                if (it.isLoading){
                    Log.d(TAG, "onCreate: It Is Loading!!!")
                }
                if (it.error.isNotBlank()){
                    Log.d(TAG, "onCreate: It Has Error!!!\t${it.error}")
                }
                if (it.users.isNotEmpty()){
                    for (user:UserModel in it.users){
                        Log.e(TAG, "onCreate: user: ${user.name}" )
                    }
                }
            }
        }

        Thread {
            try {
                Thread.sleep(5000L) // Sleep for 2 seconds
                Log.v(TAG, "onCreate: ${mainViewModel.state.value}")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start() // Start the thread
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

/**
//    private val mainViewModel: MainViewModel by viewModels()
 so use this dependency -> implementation ("androidx.fragment:fragment-ktx:1.3.2")
 */

//https://github.com/himanshuGaur684/Retrofit_Tutorials/tree/PUT_and_PATCH_using_%40Field