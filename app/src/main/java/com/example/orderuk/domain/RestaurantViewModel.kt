package com.example.orderuk.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

class RestaurantViewModel: ViewModel() {
    private val db = Firebase.firestore
    val uiState = flow<RestaurantState> {
    val products =   db.collection("product").get().await()

    val newList = products.map {snapshot->

    val product = snapshot.getData()
    Dishes(
        name = product["name"].toString(),
        category = product["category"].toString(),
        description = product["description"].toString(),
        sizes = product["sizes"] as Map<String, Number>,
        spiceLevel = product["spice_level"] as Number,
        image = product["image"].toString()
    )

}
       emit(RestaurantState.Success(newList))

   }.stateIn(
       scope = viewModelScope,
       initialValue = RestaurantState.Loading,
       started = SharingStarted.WhileSubscribed(5_000L)
   )

}