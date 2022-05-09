package com.ajgroup.simpledatastore

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(private val pref: CounterDataStoreManager) : ViewModel() {
    val vCounter: MutableLiveData<Int> = MutableLiveData(0)

    fun incrementCount(){
        vCounter.postValue(vCounter.value?.plus(1))
    }
    fun decrementCount(){
        vCounter.value?.let {
            if(it>0){
                vCounter.postValue(vCounter.value?.minus(1))
            }
        }
    }
    fun saveDataStore(value: Int) {
        viewModelScope.launch {
            pref.setCounter(value)
        }
    }

    fun getDataStore(): LiveData<Int> {
        return pref.getCounter().asLiveData()
    }
    companion object {
        private const val DATASTORE_NAME = "counter_preferences"

        private val COUNTER_KEY = intPreferencesKey("counter_key")

        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}


