package com.example.banque;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "comptes")
public class CompteList {
    private List<Compte> comptes = new ArrayList<>();

    public CompteList() {}
    public CompteList(List<Compte> comptes) { this.comptes = comptes; }

    @XmlElement(name = "compte")
    public List<Compte> getComptes() { return comptes; }
    public void setComptes(List<Compte> comptes) { this.comptes = comptes; }
}
