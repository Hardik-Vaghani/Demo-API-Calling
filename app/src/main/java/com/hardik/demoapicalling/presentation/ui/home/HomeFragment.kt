package com.hardik.demoapicalling.presentation.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hardik.demoapicalling.MainActivity
import com.hardik.demoapicalling.databinding.FragmentHomeBinding
import com.hardik.demoapicalling.domain.model.UserModel

class HomeFragment : Fragment() {
    private val TAG = HomeFragment::class.java.simpleName

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val myActivity = activity as? MainActivity
        val viewModel = myActivity?.mainViewModel
        viewModel?.let { it.state.observe(viewLifecycleOwner) {
            if (it.isLoading){
                binding.textHome.text = "Loading!!!"
            }
            if (it.error.isNotBlank()){
                binding.textHome.text = it.error
            }
            if (it.users.isNotEmpty()){
                val users = StringBuilder("")
                for (user: UserModel in it.users){
                    users.append(user.name+"\n")
                }
                binding.textHome.text= users
            }
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val data = (activity as MainActivity).mainViewModel.state.value
        Log.v(TAG, "onDestroyView: $data")
    }
}