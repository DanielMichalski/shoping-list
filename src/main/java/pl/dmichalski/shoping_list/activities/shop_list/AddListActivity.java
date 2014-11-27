package pl.dmichalski.shoping_list.activities.shop_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lista_zakupow.R;
import pl.dmichalski.shoping_list.activities.MainActivity;
import pl.dmichalski.shoping_list.database.dao.ListaZakupowDao;
import pl.dmichalski.shoping_list.shoping.ShopingList;


public class AddListActivity extends Activity {
    public static final int NO_TASK_TAME_DIALOG = 1;
    ListaZakupowDao listaZakupowDao;
    private EditText editTextTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_list);

        listaZakupowDao = new ListaZakupowDao(this);

        editTextTaskName = (EditText) findViewById(R.id.listNameEditText);
        Button saveBtn = (Button) findViewById(R.id.buttonSave);

        saveBtn.setOnClickListener(new OnSaveClickListener());
    }

    /**
     * metoda powracajaca do glownej aktywnosci
     */
    private void wrocDoGlownejAkt() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
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
            case NO_TASK_TAME_DIALOG: {
                return new AlertDialog.Builder(this)
                        .setMessage("Nie wpisałeś nazwy")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        }).create();
            }
        }
        return null;
    }

    /**
     * Sluchacz  przycisku zapisz
     */
    private class OnSaveClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (editTextTaskName.getText().toString().equals(""))
                showDialog(NO_TASK_TAME_DIALOG);
            else {
                String listaZakupowName = editTextTaskName.getText().toString();
                ShopingList shopingList = new ShopingList(listaZakupowName);
                listaZakupowDao.save(shopingList);
                wrocDoGlownejAkt();
            }
        }
    }
}
