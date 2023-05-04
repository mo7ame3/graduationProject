package com.example.graduationproject.sharedpreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedPreference(private val context: Context) {

    //to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserState")
        val USER_STATE = stringPreferencesKey("user_state")
        val TOKEN = stringPreferencesKey("token")
        val NAME = stringPreferencesKey("name")
    }

    //to get the email
    val getState: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[USER_STATE] ?: ""
        }

    val getToken: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[TOKEN] ?: ""
        }
    val getName: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[NAME] ?: ""
        }

    //to save the email
    suspend fun saveState(name: String) {
        context.dataStore.edit { Preferences ->
            Preferences[USER_STATE] = name
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { Preferences ->
            Preferences[TOKEN] = token
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { Preferences ->
            Preferences[NAME] = name
        }
    }

}
