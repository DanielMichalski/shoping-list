package pl.dmichalski.shoping_list.shoping;

import pl.dmichalski.shoping_list.adapters.GetterName;

import java.io.Serializable;

/**
 * Klasa produktu ktory będzie wykorzystywany w programie
 */
public class Product implements GetterName, Serializable {
    private long id;
    private String name;
    private double ilosc;
    private Unit jednostka;

    public Product(String name, double ilosc, Unit jednostka) {
        this.name = name;
        this.ilosc = ilosc;
        this.jednostka = jednostka;
    }

    public Product(int id, String name, double ilosc, Unit jednostka) {

        this.id = id;
        this.name = name;
        this.ilosc = ilosc;
        this.jednostka = jednostka;
    }

    public String getName() {
        return name;
    }

    public double getIlosc() {
        return ilosc;
    }

    public Unit getJednostka() {
        return jednostka;
    }

    @Override
    public String getNameToView() {
        return this.name + ", ilość: " + ilosc + " " + jednostka;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIlosc(double ilosc) {
        this.ilosc = ilosc;
    }

    public void setJednostka(Unit jednostka) {
        this.jednostka = jednostka;
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "name='" + name + '\'' +
                ", ilosc=" + ilosc +
                ", jednostka=" + jednostka +
                "} " + super.toString();
    }
}
