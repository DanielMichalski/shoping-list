package pl.dmichalski.shoping_list.activities.products;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.lista_zakupow.R;
import pl.dmichalski.shoping_list.activities.MainActivity;
import pl.dmichalski.shoping_list.adapters.SpecialAdapter;
import pl.dmichalski.shoping_list.database.dao.ProduktyDao;
import pl.dmichalski.shoping_list.shoping.Product;
import pl.dmichalski.shoping_list.shoping.ShopingList;

import java.util.List;

public class ShowProductsActivity extends Activity {
    private static final int CONFIRM_REMOVE_LIST = 1;

    SpecialAdapter<Product> adapter;
    ProduktyDao produktyDao;

    private ListView list;

    private TextView howMayTasks;

    private Button addBtn;
    private Button editBtn;
    private Button deleteBtn;
    private Button showProductsBtn;

    ShopingList lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initialize();
        setOnClickListeners();
    }

    /**
     * Inicjalizacja danych
     */
    private void initialize() {
        list = (ListView) findViewById(R.id.list);
        lista = (ShopingList) getIntent().getExtras().get("lista");

        produktyDao = new ProduktyDao(this, lista);
        List<Product> listaProduktow = produktyDao.getAllProductsByShopList();

        adapter = new SpecialAdapter<Product>(this, listaProduktow);
        list.setAdapter(adapter);

        howMayTasks = (TextView) findViewById(R.id.textViewHowMayTasks);

        addBtn = (Button) findViewById(R.id.buttonAdd);
        editBtn = (Button) findViewById(R.id.buttonEdit);
        deleteBtn = (Button) findViewById(R.id.buttonDelete);
        showProductsBtn = (Button) findViewById(R.id.showProdBtn);
        showProductsBtn.setVisibility(View.INVISIBLE);

        refreshListView();

        disableBtns();
    }

    /**
     * Ustawienie słuchaczy
     */
    private void setOnClickListeners() {
        addBtn.setOnClickListener(new ButtonAddListener());
        editBtn.setOnClickListener(new ButtonEditListener());
        deleteBtn.setOnClickListener(new ButtonDeleteListener());
        list.setOnItemClickListener(new SluchaczKliknieciaNaListe());
        list.setOnItemLongClickListener(new SluchaczListyDlugieKlikniecie());
    }

    /**
     * Włączenie przycisków
     */
    private void enableBtns() {
        addBtn.setEnabled(true);
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
    }

    /**
     * Wyłączenie przycisków
     */
    private void disableBtns() {
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    /**
     * Odświeżenie informacji o ilości produktów na liście
     */
    private void refreshListView() {
        if (list.getCount() == 0) {
            howMayTasks.setText("Brak produktów na liście zakupów: " + lista.getNameToView());
        } else {
            howMayTasks.setText("Ilość produktów na liście zakupów " + lista.getNameToView() + ": " + list.getCount());
        }
    }

    /**
     * Okno dialogowe
     *
     * @param id nr okna dialogowego
     * @return okno dialogowe
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CONFIRM_REMOVE_LIST: {
                return new AlertDialog.Builder(this)
                        .setMessage("Czy chcesz usunąć wybrany produkt?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Product selectedProduct = adapter.getSelectedItem();
                                adapter.removeItem(adapter.getSelectedItemPos());
                                adapter.unselectAll();
                                list.setAdapter(adapter);
                                produktyDao.delete(selectedProduct);
                                Toast.makeText(getApplicationContext(), "Usunięto produkt: " + selectedProduct.getName(), Toast.LENGTH_LONG).show();
                                refreshListView();
                                disableBtns();
                            }
                        }).setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
            }
        }
        return null;
    }

    /**
     * Sluchacz klikncięcia na listę
     */
    class SluchaczKliknieciaNaListe implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            adapter.setSelected(pos);
            enableBtns();
        }
    }

    /**
     * Sluchacz długiego klikncięcia na listę
     */
    class SluchaczListyDlugieKlikniecie implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
            adapter.setSelected(pos);
            showDialog(CONFIRM_REMOVE_LIST);
            return true;
        }
    }

    /**
     * Sluchacz klikncięcia na przycisk dodaj
     */
    private class ButtonAddListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Product product = adapter.getSelectedItem();
            Intent intent = new Intent(getApplicationContext(), AddAndEditProductActivity.class);
            if (product != null)
                intent.putExtra("produkt", product);
            intent.putExtra("lista", lista);
            intent.putExtra("czyEdit", "nie");
            startActivity(intent);
        }
    }

    /**
     * Sluchacz klikncięcia na przycisk edytuj
     */
    private class ButtonEditListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Product product = adapter.getSelectedItem();
            Intent intent = new Intent(getApplicationContext(), AddAndEditProductActivity.class);
            intent.putExtra("produkt", product);
            intent.putExtra("lista", lista);
            intent.putExtra("czyEdit", "tak");
            startActivity(intent);
        }
    }

    /**
     * Sluchacz klikncięcia na przycisk usuń
     */
    private class ButtonDeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Product selectedProduct = adapter.getSelectedItem();
            adapter.removeItem(adapter.getSelectedItemPos());
            adapter.unselectAll();
            list.setAdapter(adapter);
            produktyDao.delete(selectedProduct);
            Toast.makeText(getApplicationContext(), "Usunięto produkt: " + selectedProduct.getName(), Toast.LENGTH_LONG).show();
            refreshListView();
            disableBtns();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
