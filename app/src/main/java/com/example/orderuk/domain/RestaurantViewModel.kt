package com.example.orderuk.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RestaurantViewModel: ViewModel() {
    private val db = Firebase.firestore
    val uiState = callbackFlow<RestaurantState> {
    val products =   db.collection("product").get().await()

    val newList = products.map {snapshot->

    val product = snapshot.getData()
    transformProductDoc(product)

}
        trySend(RestaurantState.Success(newList)).isSuccess
        val collectionRef = Firebase.firestore.collection("product")
      val subscription =  collectionRef.addSnapshotListener{updatedProduct, error->
            if(error!=null){
                error.printStackTrace()
                return@addSnapshotListener
            }
            if(updatedProduct != null){
              val updatedList = updatedProduct.map {snapshot ->
                  val product = snapshot.getData()

                  transformProductDoc(product)
              }
                trySend(RestaurantState.Success(updatedList)).isSuccess

            }
        }

        awaitClose { subscription.remove() }

   }.stateIn(
       scope = viewModelScope,
       initialValue = RestaurantState.Loading,
       started = SharingStarted.WhileSubscribed(5_000L)
   )
private  fun transformProductDoc(product: Map<String, Any>): Dishes{
    return Dishes(
        name = product["name"].toString(),
        category = product["category"].toString(),
        description = product["description"].toString(),
        sizes = product["sizes"] as Map<String, Number>,
        spiceLevel = product["spice_level"] as Number,
        image = product["image"].toString(),
        id = product["id"].toString()
    )
}

}