package com.sports.typecode.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sports.typecode.databinding.SreenUsersBinding
import com.sports.typecode.network.UserResponse
import com.sports.typecode.utils.Status

class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SreenUsersBinding.inflate(inflater)

        adapter = UserAdapter(arrayListOf()) { id ->
            this.findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToPhotoFragment(id))
        }

        binding.apply {
            lifecycleOwner = this@UsersFragment
            viewModel = this@UsersFragment.viewModel
        }

        setupObservers()

        binding.recyclerView.adapter = adapter
        return binding.root
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let (::retrieveList)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<UserResponse>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }
}