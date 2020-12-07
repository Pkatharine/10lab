package com.katya.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katya.roomdemo.db.SubscriberRepository
import java.lang.IllegalArgumentException

class SubscriberViewModelFactory(private val repository: SubscriberRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
         return SubscriberViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }

}