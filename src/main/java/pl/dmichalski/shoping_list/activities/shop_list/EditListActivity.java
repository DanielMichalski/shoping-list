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

public class EditListActivity extends Activity {
    public static final int NO_TASK_TAME_DIALOG = 1;

    private EditText editTextTaskName;

    private ListaZakupowDao listaZakupowDao;
    ShopingList shopingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_list);

        shopingList = (ShopingList) getIntent().getExtras().get("lista");

        listaZakupowDao = new ListaZakupowDao(this);
        editTextTaskName = (EditText) findViewById(R.id.listNameEditText);
        editTextTaskName.setText(shopingList.getNameToView());

        Button saveBtn = (Button) findViewById(R.id.buttonSave);

        saveBtn.setOnClickListener(new OnSaveClickListener());
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
                shopingList.setName(editTextTaskName.getText().toString());
                listaZakupowDao.update(shopingList);
                wrocDoGlownejAkt();
            }
        }
    }

    private void wrocDoGlownejAkt() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
