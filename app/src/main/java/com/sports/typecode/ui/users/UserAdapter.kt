package com.sports.typecode.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sports.typecode.databinding.ItemUserBinding
import com.sports.typecode.network.UserResponse

class UserAdapter(
    private val users: ArrayList<UserResponse>,
    private val onUserClick: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.DataViewHolder>() {

    class DataViewHolder(private var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onUserClick(position) }
        holder.bind(users[position])
    }

    fun addUsers(users: List<UserResponse>) {
        this.users.apply {
            clear()
            addAll(users)
        }
    }
}