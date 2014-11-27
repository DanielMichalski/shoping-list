package pl.dmichalski.shoping_list.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import pl.dmichalski.shoping_list.database.utils.query.DatabaseQueryStrings;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ShopingListTableHeaders;
import pl.dmichalski.shoping_list.database.utils.tables_headers.ProductTableHeaders;

import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = DBHelper.class.getSimpleName();

    private int version;

    /**
     * Klasa pomocna przy tworzeniu/ zmiany bazy danych
     *
     * @param context Context aplikacji
     * @param name    nazwa bazy danych
     * @param version wersja bazy danych
     */
    public DBHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, "Tworzenie nowej bazy danych...");
        db.execSQL(DatabaseQueryStrings.CREATE_LIST_TABLE);
        db.execSQL(DatabaseQueryStrings.CREATE_PRODUCTS_TABLE);
        // ustawienia lokalne.
        db.setLocale(Locale.getDefault());
        // Przystosowanie SQLiteDatabase do pracy wielowątkowej
        // poprzez zastosowanie blokad w kluczowych sekcjach.
        db.setLockingEnabled(true);
        // Ustawienie wersji bazy.
        db.setVersion(version);

        // Zarejestrowanie pewnych informacji o naszej bazie danych.
        Log.i(DEBUG_TAG, "Utworzono bazę danych: " + db.getPath());
        Log.i(DEBUG_TAG, "Wersja bazy danych: " + db.getVersion());
        Log.i(DEBUG_TAG, "Wielkość strony bazy danych: " + db.getPageSize());
        Log.i(DEBUG_TAG, "Maksymalna wielkość bazy danych: " + db.getMaximumSize());
        Log.i(DEBUG_TAG, "Baza jest otworzona: " + db.isOpen());
        Log.i(DEBUG_TAG, "Baza tylko do odczytu: " + db.isReadOnly());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseQueryStrings.DROP_LIST_TABLE);
        db.execSQL(DatabaseQueryStrings.DROP_PRODUCTS_TABLE);
        Log.i(DEBUG_TAG, "Usuniecie tabeli: " + ShopingListTableHeaders.TABLE_NAME);
        Log.i(DEBUG_TAG, "Usuniecie tabeli: " + ProductTableHeaders.TABLE_NAME);
        Log.d(DEBUG_TAG, "Aktualizacja bazy danych z ver." + oldVersion + " do ver." + newVersion);
        onCreate(db);
    }
}