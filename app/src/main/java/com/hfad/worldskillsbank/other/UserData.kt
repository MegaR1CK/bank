package com.hfad.worldskillsbank.other

import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelCredit
import com.hfad.worldskillsbank.models.ModelToken
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

class UserData (val token: String) {

    init {
        runBlocking(newSingleThreadContext("INIT")) {
            updateCardList()
            updateCheckList()
            updateCreditsList()
            updateUsername()
        }
    }

    var cards = listOf<ModelCard>()
    var checks = listOf<ModelCheck>()
    var credits = listOf<ModelCredit>()
    var username: String? = null

    fun updateCardList() {
        cards = App.MAIN_API.getCards(ModelToken(token)).execute().body() ?: listOf()
    }

    fun updateCheckList() {
        checks = App.MAIN_API.getChecks(ModelToken(token)).execute().body() ?: listOf()
    }

    fun updateUsername() {
        val un = App.MAIN_API.getUser(ModelToken(token)).execute().body()
        username = un?.name + " " + un?.midname
    }

    fun updateCreditsList() {
        credits = App.MAIN_API.getCredits(ModelToken(token)).execute().body() ?: listOf()
    }
}