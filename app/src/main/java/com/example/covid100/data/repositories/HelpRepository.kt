package com.example.covid100.data.repositories

import com.example.covid100.data.FirestoreService
import com.example.covid100.data.model.HelpBody
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import kotlinx.coroutines.flow.Flow

class HelpRepository(
    private val firestoreService: FirestoreService = Injector.getInstance().providesFirestoreService()
) {

    suspend fun getAllHelpNeeded() : Flow<Result<List<HelpBody>>> {
        return firestoreService.getAllHelpNeeded()
    }

    suspend fun uploadHelpRequest(help: HelpBody) = firestoreService.uploadHelpRequest(help)

    suspend fun getHelpRequestInfo(id: String) = firestoreService.getHelpRequestInfo(id)
}