package pl.dmichalski.shoping_list.activities.products;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.lista_zakupow.R;
import pl.dmichalski.shoping_list.database.dao.ProductDao;
import pl.dmichalski.shoping_list.shoping.Product;
import pl.dmichalski.shoping_list.shoping.ShopingList;
import pl.dmichalski.shoping_list.shoping.Unit;

public class AddAndEditProductActivity extends Activity {
    public static final int NO_PRODUCT_NAME_DIALOG = 1;

    private ProductDao productDao;

    private ArrayAdapter<CharSequence> iloscAdapter;
    private ArrayAdapter<CharSequence> jednostkiAdapter;

    private Product product;
    private ShopingList shopingList;

    private Spinner iloscSpinner;
    private Spinner jednostkiSpinner;

    private EditText productNameEditText;

    private Button saveBtn;

    private boolean czyEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_product);

        product = (Product) getIntent().getExtras().get("produkt");
        shopingList = (ShopingList) getIntent().getExtras().get("lista");

        productDao = new ProductDao(getApplicationContext(), shopingList);

        productNameEditText = (EditText) findViewById(R.id.productNameEditText);

        if (getIntent().getExtras().get("czyEdit").equals("tak")) {
            czyEdit = true;
            setUpForm();
        }

        iloscSpinner = (Spinner) findViewById(R.id.ilosc_spinner);
        jednostkiSpinner = (Spinner) findViewById(R.id.jednostka_spinner);

        iloscAdapter = ArrayAdapter.createFromResource(this,
                R.array.ilosc_array, android.R.layout.simple_spinner_item);
        iloscAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iloscSpinner.setAdapter(iloscAdapter);

        jednostkiAdapter = ArrayAdapter.createFromResource(this,
                R.array.jednostki_array, android.R.layout.simple_spinner_item);
        jednostkiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jednostkiSpinner.setAdapter(jednostkiAdapter);

        saveBtn = (Button) findViewById(R.id.buttonSaveProd);
        saveBtn.setOnClickListener(new OnSaveBtnClickListener());
    }

    /**
     * Przygotowanie formularza
     */
    private void setUpForm() {
        productNameEditText.setText(product.getName());
    }

    /**
     * Słuchacz przycisku zapisz
     */
    private class OnSaveBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (productNameEditText.getText().toString().equals("")) {
                showDialog(NO_PRODUCT_NAME_DIALOG);
            } else {
                if (czyEdit) {
                    product = getProductFromFields();
                    productDao.update(product);
                    backToPreviousActivity();
                } else {
                    Product product1 = getProductFromFields();
                    productDao.save(product1);
                    backToPreviousActivity();
                }
            }
        }
    }

    /**
     * Powrót do głównej aktywności
     */
    private void backToPreviousActivity() {
        Intent intent = new Intent(getApplicationContext(), ShowProductsActivity.class);
        intent.putExtra("lista", shopingList);
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
            case NO_PRODUCT_NAME_DIALOG: {
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
     * Metoda pobierająca produkt z formularza
     *
     * @return produkt wpisany do formularza
     */
    private Product getProductFromFields() {
        if (czyEdit) {
            product.setName(productNameEditText.getText().toString());
            product.setIlosc(Double.valueOf(String.valueOf(iloscSpinner.getSelectedItem())));
            product.setJednostka(Unit.valueOf(String.valueOf(jednostkiSpinner.getSelectedItem())));
            return product;
        } else {
            String productName = productNameEditText.getText().toString();
            double ilosc = Double.valueOf(String.valueOf(iloscSpinner.getSelectedItem()));
            String jednostkaString = String.valueOf(jednostkiSpinner.getSelectedItem());
            Unit jednostka = Unit.valueOf(jednostkaString);

            return new Product(productName, ilosc, jednostka);
        }

    }
}
