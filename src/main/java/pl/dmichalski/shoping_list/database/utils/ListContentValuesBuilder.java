package pl.dmichalski.shoping_list.database.utils;

import android.content.ContentValues;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ListaZakupowTableHeaders;
import pl.dmichalski.shoping_list.shoping.ShopingList;

public class ListContentValuesBuilder {
    public static ContentValues createContentValuesByList(ShopingList shopingList) {
        ContentValues newListValues = new ContentValues();

        newListValues.put(ListaZakupowTableHeaders.LIST_NAME, shopingList.getName());
        return newListValues;
    }
}
