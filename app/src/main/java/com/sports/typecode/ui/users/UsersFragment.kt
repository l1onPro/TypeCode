package com.sports.typecode.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = UserAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        /*recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE*/
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        /*progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE*/
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