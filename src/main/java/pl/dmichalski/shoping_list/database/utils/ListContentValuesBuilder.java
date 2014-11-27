package pl.dmichalski.shoping_list.database.utils;

import android.content.ContentValues;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ShopingListTableHeaders;
import pl.dmichalski.shoping_list.shoping.ShopingList;

public class ListContentValuesBuilder {
    public static ContentValues createContentValuesByList(ShopingList shopingList) {
        ContentValues newListValues = new ContentValues();

        newListValues.put(ShopingListTableHeaders.LIST_NAME, shopingList.getName());
        return newListValues;
    }
}
