package com.example.placesdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "PlacesVM"


class PlacesViewModel : ViewModel() {
    private val _placesFlow: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())
    private val db = Firebase.firestore
    val placesFlow : StateFlow<List<Place>>
        get() = _placesFlow.asStateFlow()
    fun getPlaces(): StateFlow<List<Place>> {
        db.collection("Coordinates")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.documents != null ) {

                    val places = snapshot.documents.map { doc ->
                        val latitude = doc["Latitude"] as Double
                        val longitude = doc["Longitude"] as Double
                        Place(latitude, longitude)
                    }
                    Log.d(TAG, "Number of places: ${places.size}")
                    _placesFlow.value = places
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
        return _placesFlow
    }


    fun addCoords(lat: Double? , long:Double?) {
        Log.d(TAG, "Called add func")

        val data = hashMapOf<String , Double? > (
            "Latitude" to lat,
            "Longitude" to long
        )

        Log.d(TAG, "data/map made:  " + data)
// Add a new document with a generated ID
        db.collection("Coordinates")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }




}