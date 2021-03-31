package com.example.tawkapp.adapters

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.tawkapp.OnItemClickListener
import com.example.tawkapp.R
import com.example.tawkapp.databinding.ItemUserBinding
import com.example.tawkapp.model.users.Users
import javax.inject.Inject

class UsersAdapter @Inject constructor(

) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var list: List<Users> = mutableListOf()
    private lateinit var onItemClickListener: OnItemClickListener

    class UsersViewHolder private constructor(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            userModel: Users,
            position: Int,
            onItemClickListener: OnItemClickListener
        ) {
            val currentPosition = position + 1

            binding.imgAvatar.clearColorFilter()
            if (currentPosition % 4 == 0) {
                Glide.with(binding.imgAvatar.context)
                    .asBitmap()
                    .load(userModel.avatar_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            binding.imgAvatar.setImageBitmap(resource)
                            val colorMatrix = ColorMatrix()
                            colorMatrix.setSaturation(0f)
                            val filter = ColorMatrixColorFilter(colorMatrix)
                            binding.imgAvatar.colorFilter = filter
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            } else {
                Glide.with(binding.imgAvatar.context)
                    .load(userModel.avatar_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgAvatar)
            }
            binding.txtName.text = userModel.login

            if (userModel.notes != null && userModel.notes!!.length > 0)
                binding.imgNotes.visibility = View.VISIBLE
            else
                binding.imgNotes.visibility = View.GONE

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(userModel)
            }
        }

        companion object {

            fun from(parent: ViewGroup): UsersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UsersViewHolder(binding)
            }

        }
    }

    fun setOnItemClickListener(
        onItemClickListener: OnItemClickListener
    ){
        this.onItemClickListener = onItemClickListener
    }

    fun setUsersList(
        list: List<Users>
    ) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = list[position]
        item.let { holder.bind(it, position, onItemClickListener) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}