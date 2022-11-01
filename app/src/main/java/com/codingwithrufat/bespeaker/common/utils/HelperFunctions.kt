package com.codingwithrufat.bespeaker.common.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.codingwithrufat.bespeaker.common.storage.DataStore
import java.text.SimpleDateFormat
import java.util.*


fun Uri.getFileExtension(context: Context): String {
    val contentResolver = context.contentResolver
    val mimeTypeMap = MimeTypeMap.getSingleton()
    return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(this)).toString()
}

// format -> 2022-10-5 11:53
fun getCurrentTime(): String {
    val today = Date()
    val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
    return sdf.format(today)
}

// get the name of enum by its value
fun getEnglishClassName(value: String): String {
    for (englishLevel in EnglishLevel.values()) {
        if (englishLevel.string == value) {
            return englishLevel.name
        }
    }
    return ""
}

suspend fun writeUserInformationsToDataStore(
    context: Context,
    userID: String,
    username: String,
    englishLevel: EnglishLevel,
    profileImageLink: String
) {

    val store = DataStore(context)

    store.saveString("user_id", userID)
    store.saveString("username", username)
    store.saveString("english_level", englishLevel.name)
    store.saveString("profile_image_link", profileImageLink)

}