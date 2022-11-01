package com.codingwithrufat.bespeaker.features.feature_home.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingwithrufat.bespeaker.R
import com.codingwithrufat.bespeaker.common.listeners.OnClickedCallButtonListener
import com.codingwithrufat.bespeaker.common.storage.DataStore
import com.codingwithrufat.bespeaker.common.utils.*
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.CallNotification
import com.codingwithrufat.bespeaker.features.feature_home.domain.model.HomeUsersItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*

class UserItemAdapter(
    private val context: Context,
    private var list: List<HomeUsersItem>,
    private val listener: OnClickedCallButtonListener,
    private val coroutineScope: CoroutineScope
) : RecyclerView.Adapter<UserItemAdapter.ViewHolder>() {

    val dataStore = DataStore(context)

    fun updateList(newList: List<HomeUsersItem>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_user_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(list[position].profile_image_link)
            .into(holder.profileImageLink)

        holder.name.text = "${list[position].name}"
        holder.englishLevel.text = getEnglishClassName(list[position].english_level!!)

        holder.icVideoCall.setOnClickListener {

            // TODO all user informations will be saved in data store
            var username: String? = null
            var imageLink: String? = null
            var englishLevel: String? = null
            var userID: String? = null

            coroutineScope.launch {
                username = dataStore.readString("username")
                englishLevel = dataStore.readString("english_level")
                imageLink = dataStore.readString("profile_image_link")
                userID = dataStore.readString("user_id")
            }

            listener.onClickButton(
                "${list[position].id}", CallNotification(
                    UUID.randomUUID().toString(),
                    userID,
                    list[position].id,
                    username,
                    EnglishLevel.B2,
                    CallType.VIDEO,
                    imageLink
                )
            )

            if (Permissions.checkCallPermission(context)) {
                val bundle = Bundle()
                bundle.putString("call_receiver_name", list[position].name)
                bundle.putString("call_receiver_id", list[position].id)
                bundle.putString("caller_name", "Someone")
                Navigation.findNavController(it).navigate(R.id.videoCallFragment)
            }

        }

    }

    override fun getItemCount(): Int {
        if (list.isEmpty()) {
            return 0
        }
        return list.size
    }

    class ViewHolder(item_view: View) : RecyclerView.ViewHolder(item_view) {

        val profileImageLink: ImageView = item_view.findViewById(R.id.profileImage)
        val name: TextView = item_view.findViewById(R.id.txt_username)
        val englishLevel: TextView = item_view.findViewById(R.id.txt_englishLevel)
        val icPhoneCall: ImageView = item_view.findViewById(R.id.ic_phoneCall)
        val icVideoCall: ImageView = item_view.findViewById(R.id.ic_videoCall)

    }

}