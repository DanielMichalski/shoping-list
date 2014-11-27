package pl.dmichalski.shoping_list.shoping;

import pl.dmichalski.shoping_list.adapters.GetterName;

import java.io.Serializable;

/**
 * Lista zakupow ktora ma nazwe oraz id
 */
public class ShopingList implements GetterName, Serializable {
    private String name;
    private long id;

    public ShopingList(String name) {
        this.name = name;
    }

    public ShopingList(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getNameToView() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ListaZakupow{" +
                "name='" + name + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
