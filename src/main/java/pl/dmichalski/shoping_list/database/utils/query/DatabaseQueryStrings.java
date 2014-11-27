package pl.dmichalski.shoping_list.database.utils.query;

import pl.dmichalski.shoping_list.database.utils.tables_headers.ShopingListTableHeaders;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProductTableHeaders;

public class DatabaseQueryStrings {
    // Polecenia SQL do tworzenia i usuwania tabel.
    public static final String CREATE_LIST_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ShopingListTableHeaders.TABLE_NAME +
                    "(" +
                    ShopingListTableHeaders.LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ShopingListTableHeaders.LIST_NAME + " TEXT" +
                    ");";


    // Usuwanie tabel
    public static final String DROP_LIST_TABLE =
            "DROP TABLE IF EXISTS " + ShopingListTableHeaders.TABLE_NAME + ";";

    public static final String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ProductTableHeaders.TABLE_NAME +
                    "(" +
                    ProductTableHeaders.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProductTableHeaders.PRODUCT_NAME + " TEXT, " +
                    ProductTableHeaders.HOW_MANY + " DECIMAL NOT NULL, " +
                    ProductTableHeaders.UNIT + " TEXT, " +
                    ProductTableHeaders.LIST_ID + " INTEGER NOT NULL" +
                    ");";


    // Usuwanie tabel
    public static final String DROP_PRODUCTS_TABLE =
            "DROP TABLE IF EXISTS " + ProductTableHeaders.TABLE_NAME + ";";


}
