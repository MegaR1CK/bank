package com.hfad.worldskillsbank;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class ValCurs {

    @Attribute(name = "Date")
    String date;

    @Attribute(name = "name")
    String name;

    @ElementList(name = "Valute", inline = true)
    List<Valute> valutes;
}

