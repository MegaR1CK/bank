package com.hfad.worldskillsbank.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs")
data class ModelValCurs @JvmOverloads constructor(
        @field:Attribute(name = "Date") var date: String = "",
        @field:Attribute(name = "name") var name: String = "",
        @field:ElementList(name = "Valute", inline = true)
        var valutes: MutableList<ModelValute> = mutableListOf())