package com.example.learning_assignment1

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    @ApplicationContext private val context: Context) {

    private val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(
        fileName = "user_prefs.pb",
        serializer = UserPreferencesSerializer
    )

    suspend fun writeToLocal(name:String,phone:String,email:String) = context.userPreferencesStore.updateData{ UserPreferences->
        UserPreferences.toBuilder()
            .setName(name)
            .setPhone(phone)
            .setEmail(email)
            .build()
    }
    val readToLocal : Flow<Users> = context.userPreferencesStore.data
        .catch {
            if (this is Exception){
                Log.d("main","${this.message}")
            }
        }.map {
            val users = Users(it.name,it.phone,it.email)
            users
        }

}