package pl.dmichalski.shoping_list.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.lista_zakupow.R;
import pl.dmichalski.shoping_list.activities.products.ShowProductsActivity;
import pl.dmichalski.shoping_list.activities.shop_list.AddListActivity;
import pl.dmichalski.shoping_list.activities.shop_list.EditListActivity;
import pl.dmichalski.shoping_list.adapters.SpecialAdapter;
import pl.dmichalski.shoping_list.database.dao.ShopingListDao;
import pl.dmichalski.shoping_list.shoping.ShopingList;

import java.util.List;

/**
 * Główna aktywność (jest odpalana przy starcie aplikacji)
 */
public class MainActivity extends Activity {
    private static final int CONFIRM_REMOVE_LIST = 1;

    TextView howMayTasks;
    SpecialAdapter<ShopingList> adapter;
    ShopingListDao shopingListDao;
    private ListView list;
    private Button buttonEdit;
    private Button buttonDelete;

    private Button showProdBtn;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshListView();
    }

    /**
     * Czynnosci ktore beda wykonane przy starcie aplikacji
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = (ListView) findViewById(R.id.list);

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonAdd.setOnClickListener(new ButtonAddListener());
        buttonEdit.setOnClickListener(new ButtonEditListener());
        buttonDelete.setOnClickListener(new ButtonDeleteListener());

        showProdBtn = (Button) findViewById(R.id.showProdBtn);

        showProdBtn.setVisibility(View.INVISIBLE);

        showProdBtn.setOnClickListener(new OnShowProdClickListener());

        disableBtns();

        howMayTasks = (TextView) findViewById(R.id.textViewHowMayTasks);

        shopingListDao = new ShopingListDao(this);
        List<ShopingList> shopingList = shopingListDao.getAllListaZakupow();

        adapter = new SpecialAdapter<ShopingList>(this, shopingList);
        list.setAdapter(adapter);

        refreshListView();

        list.setOnItemLongClickListener(new SluchaczListyDlugieKlikniecie());

        list.setOnItemClickListener(new SluchaczKliknieciaNaListe());

        shopingListDao = new ShopingListDao(this);

    }

    /**
     * włączenie przysisków (jesli wybierzemy listę zakupów)
     */
    private void enableBtns() {
        buttonEdit.setEnabled(true);
        buttonDelete.setEnabled(true);
        showProdBtn.setVisibility(View.VISIBLE);
    }

    /**
     * wyłączenie przycisków
     */
    private void disableBtns() {
        buttonEdit.setEnabled(false);
        buttonDelete.setEnabled(false);
        showProdBtn.setVisibility(View.INVISIBLE);
    }

    /**
     * Odświeżenie informacji o liscie z zakupami
     */
    private void refreshListView() {
        if (list.getCount() == 0) {
            howMayTasks.setText("Brak listy zakupów");
        } else {
            howMayTasks.setText("Ilość listy zakupów: " + list.getCount());
        }
    }

    /**
     * Utworzenie dialogu odpowiedzialnego za potwierdzenie usunięcia listy
     *
     * @param id ktory dialog mamy utworzyc
     * @return okno dialogowe
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CONFIRM_REMOVE_LIST: {
                return new AlertDialog.Builder(this)
                        .setMessage("Czy chcesz usunąć wybraną listę?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                ShopingList selectedItem = adapter.getSelectedItem();
                                adapter.removeItem(adapter.getSelectedItemPos());
                                adapter.unselectAll();
                                list.setAdapter(adapter);
                                shopingListDao.delete(selectedItem);
                                Toast.makeText(getApplicationContext(), "Usunięto listę: " + selectedItem.getName(), Toast.LENGTH_LONG).show();
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
     * Klikniecie na element listy
     */
    class SluchaczKliknieciaNaListe implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            adapter.setSelected(pos);
            enableBtns();
        }
    }

    /**
     * Dlugie klikniecie na element listy
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
     * Klikciecie w przycisk dodaj
     */
    private class ButtonAddListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AddListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Klikciecie w przycisk edytuj
     */
    private class ButtonEditListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ShopingList selectedItem = adapter.getSelectedItem();
            Intent intent = new Intent(getApplicationContext(), EditListActivity.class);
            intent.putExtra("lista", selectedItem);
            startActivity(intent);
        }
    }

    /**
     * Klikciecie w przycisk usuń
     */
    private class ButtonDeleteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ShopingList selectedItem = adapter.getSelectedItem();
            adapter.removeItem(adapter.getSelectedItemPos());
            adapter.unselectAll();
            list.setAdapter(adapter);
            shopingListDao.delete(selectedItem);
            Toast.makeText(getApplicationContext(), "Usunięto listę: " + selectedItem.getName(), Toast.LENGTH_LONG).show();
            refreshListView();
            disableBtns();
        }
    }

    /**
     * Klikciecie w przycisk pokaż produkty
     */
    private class OnShowProdClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ShopingList selectedItem = adapter.getSelectedItem();
            Intent intent = new Intent(getApplicationContext(), ShowProductsActivity.class);
            intent.putExtra("lista", selectedItem);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
