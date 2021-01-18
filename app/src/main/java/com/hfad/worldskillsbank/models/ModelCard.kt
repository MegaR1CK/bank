package com.hfad.worldskillsbank.models

data class ModelCard (val cardNumber: String,
                      var cardType: String,
                      val balance: Double,
                      val owner: String,
                      val isBlocked: Boolean)