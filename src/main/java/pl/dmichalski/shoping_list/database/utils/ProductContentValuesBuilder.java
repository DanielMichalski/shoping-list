package pl.dmichalski.shoping_list.database.utils;

import android.content.ContentValues;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProduktyTableHeaders;
import pl.dmichalski.shoping_list.shoping.Product;

public class ProductContentValuesBuilder {

    public static ContentValues createContentValuesByProduct(Product product, long listId) {
        ContentValues newProductValues = new ContentValues();

        newProductValues.put(ProduktyTableHeaders.PRODUCT_NAME, product.getName());
        newProductValues.put(ProduktyTableHeaders.HOW_MANY, product.getIlosc());
        newProductValues.put(ProduktyTableHeaders.UNIT, product.getJednostka().toString());
        newProductValues.put(ProduktyTableHeaders.LIST_ID, listId);

        return newProductValues;
    }
}
