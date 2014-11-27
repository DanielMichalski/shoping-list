package pl.dmichalski.shoping_list.database.utils.query;

import pl.dmichalski.shoping_list.database.utils.tables_headers.ListaZakupowTableHeaders;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProduktyTableHeaders;

public class DatabaseQueryStrings {
    // Polecenia SQL do tworzenia i usuwania tabel.
    public static final String CREATE_LIST_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ListaZakupowTableHeaders.TABLE_NAME +
                    "(" +
                    ListaZakupowTableHeaders.LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ListaZakupowTableHeaders.LIST_NAME + " TEXT" +
                    ");";


    // Usuwanie tabel
    public static final String DROP_LIST_TABLE =
            "DROP TABLE IF EXISTS " + ListaZakupowTableHeaders.TABLE_NAME + ";";

    public static final String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ProduktyTableHeaders.TABLE_NAME +
                    "(" +
                    ProduktyTableHeaders.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProduktyTableHeaders.PRODUCT_NAME + " TEXT, " +
                    ProduktyTableHeaders.HOW_MANY + " DECIMAL NOT NULL, " +
                    ProduktyTableHeaders.UNIT + " TEXT, " +
                    ProduktyTableHeaders.LIST_ID + " INTEGER NOT NULL" +
                    ");";


    // Usuwanie tabel
    public static final String DROP_PRODUCTS_TABLE =
            "DROP TABLE IF EXISTS " + ProduktyTableHeaders.TABLE_NAME + ";";


}
