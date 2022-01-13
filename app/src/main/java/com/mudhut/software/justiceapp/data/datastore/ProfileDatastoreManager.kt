package com.mudhut.software.justiceapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileDatastoreManager @Inject constructor(private val context: Context) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "profile")

    suspend fun writeToDataStore(key: String, value: String) {
        context.datastore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    fun readFromDataStore(key: String): Flow<String> = flow {
        context
            .datastore
            .data
            .map { preferences ->
                preferences[stringPreferencesKey(key)] ?: " "
            }
    }

}
