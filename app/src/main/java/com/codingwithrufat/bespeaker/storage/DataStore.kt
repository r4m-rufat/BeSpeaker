package com.codingwithrufat.bespeaker.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStore constructor(private val context: Context){

    private val Context.store: DataStore<Preferences> by preferencesDataStore(
        name = "user_store"
    )

    suspend fun saveString(key: String, value: String) {

        val dsk = stringPreferencesKey(key)
        context.store.edit { user ->
            user[dsk] = value
        }

    }

    suspend fun readString(key: String): String {

        val dsk = stringPreferencesKey(key)
        val data = context.store.data.first()
        return data[dsk] ?: ""

    }

    suspend fun saveInteger(key: String, value: Int) {

        val dsk = intPreferencesKey(key)
        context.store.edit { user ->
            user[dsk] = value
        }

    }

    suspend fun readInteger(key: String): Int {

        val dsk = intPreferencesKey(key)
        val data = context.store.data.first()
        return data[dsk] ?: 0

    }

    suspend fun saveBoolean(key: String, value: Boolean) {

        val dsk = booleanPreferencesKey(key)
        context.store.edit { user ->
            user[dsk] = value
        }

    }

    suspend fun readBoolean(key: String): Boolean {

        val dsk = booleanPreferencesKey(key)
        val data = context.store.data.first()
        return data[dsk] ?: false

    }

}