package com.dicodingg.bangkit.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {


    private val NOTIFICATION_KEY = booleanPreferencesKey("notification_setting")

    fun getNotificationSetting(): Flow<Boolean> { // New method for notification
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION_KEY] ?: true // Default to true if not set
        }
    }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean) { // New method for notification
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isNotificationActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}