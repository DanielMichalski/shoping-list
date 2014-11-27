package pl.dmichalski.shoping_list.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import pl.dmichalski.shoping_list.database.DBHelper;
import pl.dmichalski.shoping_list.database.adapters.DatabaseAdapter;
import pl.dmichalski.shoping_list.database.exceptions.OperationFailedException;
import pl.dmichalski.shoping_list.database.exceptions.RecordNotFoundException;
import pl.dmichalski.shoping_list.database.utils.ListUtilities;
import pl.dmichalski.shoping_list.database.utils.ProductContentValuesBuilder;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProduktyTableHeaders;
import pl.dmichalski.shoping_list.shoping.Product;
import pl.dmichalski.shoping_list.shoping.ShopingList;

import java.util.List;

public class ProduktyDao {
    private final String tableName = ProduktyTableHeaders.TABLE_NAME;

    private DatabaseAdapter databaseAdapter;

    private SQLiteDatabase database;

    private ShopingList lista;

    public ProduktyDao(Context context, ShopingList shopingList) {
        databaseAdapter = DatabaseAdapter.getInstance();
        database = databaseAdapter.open(context);
        this.lista = shopingList;
    }

    public ProduktyDao(DBHelper dbHelper, ShopingList shopingList) {
        databaseAdapter = DatabaseAdapter.getInstance();
        database = databaseAdapter.open(dbHelper);
        this.lista = shopingList;
    }

    public List<Product> getAllProductsByShopList() {
        long listId = lista.getId();
        String where = "id_listy = ?";
        Cursor cursor = database.query(tableName, null, where, new String[]{String.valueOf(listId)}, null, null, null);
        List<Product> listaProduktow = ListUtilities.getAllProductsFromCursorObiect(cursor);
        cursor.close();
        return listaProduktow;
    }

    public Product save(Product product) {
        ContentValues newTaskValues = ProductContentValuesBuilder.createContentValuesByProduct(product, lista.getId());
        long res = database.insert(tableName, null, newTaskValues);
        validDBOperation(res);
        product.setId(res);
        return product;
    }

    public Product update(Product product) {
        String where = ProduktyTableHeaders.PRODUCT_ID + "=" + product.getId();
        ContentValues updateProductValues = ProductContentValuesBuilder.createContentValuesByProduct(product, lista.getId());
        long res = database.update(tableName, updateProductValues, where, null);
        validDBOperation(res);
        return product;
    }


    public int delete(Product product) {
        String where = ProduktyTableHeaders.PRODUCT_ID + "=" + product.getId();
        int res = database.delete(tableName, where, null);
        validDBOperation(res);
        return res;
    }

    private void validDBOperation(long res) {
        if (res == -1) {
            throw new OperationFailedException();
        }
        if (res == 0) {
            throw new RecordNotFoundException();
        }
    }
}

