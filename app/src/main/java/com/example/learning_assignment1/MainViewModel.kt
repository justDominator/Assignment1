package com.example.learning_assignment1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
    constructor(private val mainRepository: UserDetailsRepository): ViewModel(){

        fun writeToLocal(name: String,phone:String,email:String) = viewModelScope.launch {
            mainRepository.writeToLocal(name, phone, email)
        }
        val readToLocal = mainRepository.readToLocal

}