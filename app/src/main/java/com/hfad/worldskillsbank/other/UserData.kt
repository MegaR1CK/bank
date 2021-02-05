package com.hfad.worldskillsbank.other

import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.models.*
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

class UserData (val token: String) {

    lateinit var cards: List<ModelCard>
    lateinit var checks: List<ModelCheck>
    lateinit var credits: List<ModelCredit>
    lateinit var transactions: List<ModelTransaction>
    lateinit var username: String

    init {
        runBlocking(newSingleThreadContext("INIT")) {
            updateCardList()
            updateCheckList()
            updateCreditsList()
            updateUsername()
            updateTransactions()
        }
    }

    fun updateCardList() {
        cards = App.MAIN_API.getCards(ModelToken(token)).execute().body() ?: listOf()
    }

    fun updateCheckList() {
        checks = App.MAIN_API.getChecks(ModelToken(token)).execute().body() ?: listOf()
    }

    private fun updateUsername() {
        val un = App.MAIN_API.getUser(ModelToken(token)).execute().body()
        username = un?.name + " " + un?.midname
    }

    private fun updateCreditsList() {
        credits = App.MAIN_API.getCredits(ModelToken(token)).execute().body() ?: listOf()
    }

    fun updateTransactions() {
        transactions = App.MAIN_API.getTransactions(ModelToken(token)).execute().body() ?: listOf()
    }
}