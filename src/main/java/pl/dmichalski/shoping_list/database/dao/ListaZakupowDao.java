package pl.dmichalski.shoping_list.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import pl.dmichalski.shoping_list.database.DBHelper;
import pl.dmichalski.shoping_list.database.adapters.DatabaseAdapter;
import pl.dmichalski.shoping_list.database.exceptions.OperationFailedException;
import pl.dmichalski.shoping_list.database.exceptions.RecordNotFoundException;
import pl.dmichalski.shoping_list.database.utils.ListContentValuesBuilder;
import pl.dmichalski.shoping_list.database.utils.ListUtilities;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ListaZakupowTableHeaders;
import pl.dmichalski.shoping_list.shoping.ShopingList;

import java.util.List;

public class ListaZakupowDao {

    private final String tableName = ListaZakupowTableHeaders.TABLE_NAME;

    private DatabaseAdapter databaseAdapter;

    private SQLiteDatabase database;


    public ListaZakupowDao(Context context) {
        databaseAdapter = DatabaseAdapter.getInstance();
        database = databaseAdapter.open(context);
    }

    public ListaZakupowDao(DBHelper dbHelper) {
        databaseAdapter = DatabaseAdapter.getInstance();
        database = databaseAdapter.open(dbHelper);
    }

    public List<ShopingList> getAllListaZakupow() {
        Cursor cursor = database.query(tableName, null, null, null, null, null, null);
        List<ShopingList> shopingList = ListUtilities.getAllListaZakupowFromCursorObiect(cursor);
        cursor.close();
        return shopingList;
    }

    public ShopingList save(ShopingList shopingList) {
        ContentValues newTaskValues = ListContentValuesBuilder.createContentValuesByList(shopingList);
        long res = database.insert(tableName, null, newTaskValues);
        validDBOperation(res);
        shopingList.setId(res);
        return shopingList;
    }

    public ShopingList update(ShopingList shopingList) {
        String where = ListaZakupowTableHeaders.LIST_ID + "=" + shopingList.getId();
        ContentValues updateTaskValues = ListContentValuesBuilder.createContentValuesByList(shopingList);
        long res = database.update(tableName, updateTaskValues, where, null);
        validDBOperation(res);
        return shopingList;
    }


    public int delete(ShopingList shopingList) {
        String where = ListaZakupowTableHeaders.LIST_ID + "=" + shopingList.getId();
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
