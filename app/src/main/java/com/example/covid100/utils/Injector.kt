package com.example.covid100.utils

import com.example.covid100.data.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Injector private constructor() {

    private var firestore : FirebaseFirestore? = null
    private var firestoreService : FirestoreService? = null

    fun providesFirestore() : FirebaseFirestore {
        if (firestore == null) {
            firestore = Firebase.firestore
        }

        return firestore!!
    }

    fun providesFirestoreService() : FirestoreService {
        if(firestoreService == null) {
            firestoreService = FirestoreService()
        }

        return firestoreService!!
    }

    companion object {
        private var instance : Injector? = null
        fun getInstance() : Injector {
            if(instance == null) {
                instance = Injector()
            }

            return instance!!
        }
    }
}