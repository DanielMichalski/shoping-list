package pl.dmichalski.shoping_list.database.utils;

import android.content.ContentValues;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProductTableHeaders;
import pl.dmichalski.shoping_list.shoping.Product;

public class ProductContentValuesBuilder {

    public static ContentValues createContentValuesByProduct(Product product, long listId) {
        ContentValues newProductValues = new ContentValues();

        newProductValues.put(ProductTableHeaders.PRODUCT_NAME, product.getName());
        newProductValues.put(ProductTableHeaders.HOW_MANY, product.getIlosc());
        newProductValues.put(ProductTableHeaders.UNIT, product.getJednostka().toString());
        newProductValues.put(ProductTableHeaders.LIST_ID, listId);

        return newProductValues;
    }
}
