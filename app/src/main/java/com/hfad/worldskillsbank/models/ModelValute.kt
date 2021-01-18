package com.hfad.worldskillsbank.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Valute")
data class ModelValute @JvmOverloads constructor(
        @field:Attribute(name = "ID") var ID: String = "",
        @field:Element(name = "NumCode") var NumCode: Int = 0,
        @field:Element(name = "CharCode") var CharCode: String = "",
        @field:Element(name = "Nominal") var Nominal: Int = 0,
        @field:Element(name = "Name") var Name: String = "",
        @field:Element(name = "Value") var Value: String = "")