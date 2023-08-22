package com.example.paging.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paging.databinding.UserDataBinding
import com.example.paging.model.User


class UserPagerAdapter(clikListner : ClickListner
): PagingDataAdapter<User, UserPagerAdapter.UserViewHolder>(UserComparator) {

    private var clikListner: ClickListner = clikListner

    interface ClickListner {
        fun clikedItem(user: User)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)!!
        holder.view.name.text = user.firstName
        Glide.with(holder.itemView.context).load(user.image).into(holder.view.image)

        holder.itemView.setOnClickListener {
            clikListner.clikedItem(user)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserDataBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    inner class UserViewHolder(val view: UserDataBinding) : RecyclerView.ViewHolder(view.root) {
        val tvName = view.name
    }


    object UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}



