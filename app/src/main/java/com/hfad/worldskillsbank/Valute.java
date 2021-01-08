package com.hfad.worldskillsbank;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute")
public class Valute {

    @Attribute(name = "ID")
    String ID;

    @Element(name = "NumCode")
    Integer NumCode;

    @Element(name = "CharCode")
    String CharCode;

    @Element(name = "Nominal")
    Integer Nominal;

    @Element(name = "Name")
    String Name;

    @Element(name = "Value")
    String Value;
}
