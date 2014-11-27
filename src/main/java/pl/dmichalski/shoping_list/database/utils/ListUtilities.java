package pl.dmichalski.shoping_list.database.utils;

import android.database.Cursor;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ShopingListTableHeaders;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProductTableHeaders;
import pl.dmichalski.shoping_list.shoping.Product;
import pl.dmichalski.shoping_list.shoping.ShopingList;
import pl.dmichalski.shoping_list.shoping.Unit;

import java.util.ArrayList;
import java.util.List;

public class ListUtilities {
    public static List<ShopingList> getAllListaZakupowFromCursorObiect(Cursor c) {
        List<ShopingList> listArray = new ArrayList<ShopingList>();
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            ShopingList shopingList = createListBy(c);
            listArray.add(shopingList);
            c.moveToNext();
        }
        return listArray;
    }

    public static List<Product> getAllProductsFromCursorObiect(Cursor c) {
        List<Product> produktsList = new ArrayList<Product>();
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Product product = createProductBy(c);
            produktsList.add(product);
            c.moveToNext();
        }
        return produktsList;
    }

    private static ShopingList createListBy(Cursor c) {
        int listId = c.getInt(c.getColumnIndex(ShopingListTableHeaders.LIST_ID));
        String listName = c.getString(c.getColumnIndex(ShopingListTableHeaders.LIST_NAME));

        return new ShopingList(listId, listName);
    }

    private static Product createProductBy(Cursor c) {
        int productId = c.getInt(c.getColumnIndex(ProductTableHeaders.PRODUCT_ID));
        String productName = c.getString(c.getColumnIndex(ProductTableHeaders.PRODUCT_NAME));
        int howMany = c.getInt(c.getColumnIndex(ProductTableHeaders.HOW_MANY));
        String unit = c.getString(c.getColumnIndex(ProductTableHeaders.UNIT));
        Unit jednostka = Unit.valueOf(unit);

        return new Product(productId, productName, howMany, jednostka);
    }
}
