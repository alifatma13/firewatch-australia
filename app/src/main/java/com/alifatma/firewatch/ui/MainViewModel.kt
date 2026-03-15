package com.alifatma.firewatch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alifatma.firewatch.data.RfsProperties
import com.alifatma.firewatch.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    val incidents = MutableStateFlow<List<RfsProperties>>(emptyList())
    val error = MutableStateFlow<String?>(null)

    fun load(){
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getMajorIncidents()
                incidents.value = result.features.map { it.properties }
                result.features.forEach {
                    Log.d(TAG, "[${it.properties.category}] ${it.properties.title}")
                }
            }
            catch (e: Exception){
                Log.e(TAG, "failed to fetch: ${e.message}")
                error.value = e.message
            }
        }
    }
}