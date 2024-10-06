package com.hardik.demoapicalling.presentation.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hardik.demoapicalling.MyApp
import com.hardik.demoapicalling.data.remote.dto.PostDto
import com.hardik.demoapicalling.databinding.FragmentNotificationsBinding
import com.hardik.demoapicalling.domain.model.PostModel
import com.hardik.demoapicalling.domain.use_case.CreatePostUseCase
import com.hardik.demoapicalling.presentation.viewModelFactory
import com.hardik.demoapicalling.utilities.ConnectivityUtil

class NotificationsFragment : Fragment() {
    private val TAG = NotificationsFragment::class.java.simpleName
    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var notificationsViewModel: NotificationsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        notificationsViewModel = ViewModelProvider(this, viewModelFactory { NotificationsViewModel(CreatePostUseCase(MyApp.appModule.userRepository)) }
        ).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) { textView.text = it }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val connectivityUtil = ConnectivityUtil(context = requireContext())

        if (connectivityUtil.hasInternetConnection()){
            notificationsViewModel.createPost(PostModel(body = "hi ther!!",id =1, title = "I'm now free!", userId = 12))
        }else{
            Toast.makeText(requireContext(),"Network Issue!!!",Toast.LENGTH_LONG).show()
        }

        notificationsViewModel.state.observe(viewLifecycleOwner){
            it?.let {createPostState ->
                Log.e(TAG, "onViewCreated: $createPostState" )

                binding.textNotifications.text = when {
                    createPostState.isLoading -> "Loading!!!"
                    createPostState.error.isNotBlank() -> createPostState.error
                    createPostState.post != null -> createPostState.post.body
                    else -> "" // Default case, if none of the above conditions are met
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.textNotifications.text = ""
        _binding = null
    }
}