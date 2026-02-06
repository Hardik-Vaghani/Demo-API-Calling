package com.hardik.demoapicalling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hardik.demoapicalling.domain.model.UserModel
import com.hardik.demoapicalling.domain.use_case.GetUserUseCase
import com.hardik.demoapicalling.presentation.MainViewModel
import com.hardik.demoapicalling.presentation.viewModelFactory

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private final val TAG = MainActivity::class.java.simpleName

//    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val mainViewModel = viewModel(factory = viewModelFactory { MainViewModel(GetUserUseCase(MyApplication.appModule.userRepository)) })
        val mainViewModel = ViewModelProvider(
            this,
            viewModelFactory { MainViewModel(GetUserUseCase(MyApplication.appModule.userRepository)) }
        ).get(MainViewModel::class.java)

//        val state = mainViewModel.state.value
//        state?.let {
//            if (state.isLoading){
//                Log.d(TAG, "onCreate: It Is Loading!!!")
//            }
//            if (state.error.isNotBlank()){
//                Log.d(TAG, "onCreate: It Has Error!!!\t${state.error}")
//            }
//            if (state.users.isNotEmpty()){
//                for (user:UserModel in state.users){
//                    Log.e(TAG, "onCreate: user: ${user.name}" )
//                }
//            }
//        }
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
    }
}