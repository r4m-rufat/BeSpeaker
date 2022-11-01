package com.codingwithrufat.bespeaker.features.feature_home.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.common.listeners.OnClickedAcceptOrRefuseListener
import com.codingwithrufat.bespeaker.common.utils.TAG
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class CallNotificationItemAdapter(
    private val context: Context,
    private var list: List<CallNotification>,
    private val listener: OnClickedAcceptOrRefuseListener
): RecyclerView.Adapter<CallNotificationItemAdapter.ViewHolder>() {

    fun updateList(newList: List<CallNotification>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_call_notification_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.username.text = list[position].sender_name
        holder.englishLevel.text = list[position].sender_english_level?.name
        Glide.with(context)
            .load(list[position].sender_image_url)
            .into(holder.profileImage)

        holder.icAcceptCallNotification.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.videoCallFragment)
        }

        holder.icRefuseCallNotification.setOnClickListener {
            list[position].receiver_id?.let {
                listener.onClickedButton(it)
            }
            Navigation.findNavController(it).navigate(R.id.videoCallFragment)
        }

    }

    override fun getItemCount(): Int {
        if (list.isEmpty()) {
            return 0
        }
        return list.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val profileImage = itemView.findViewById(R.id.profileImage) as ImageView
        val username = itemView.findViewById(R.id.txt_username) as TextView
        val englishLevel = itemView.findViewById(R.id.txt_englishLevel) as TextView
        val icAcceptCallNotification = itemView.findViewById(R.id.ic_acceptCallNotification) as ImageView
        val icRefuseCallNotification = itemView.findViewById(R.id.ic_refuseCallNotification) as ImageView

    }

}