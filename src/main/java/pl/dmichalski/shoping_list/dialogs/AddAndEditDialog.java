package pl.dmichalski.shoping_list.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lista_zakupow.R;
import pl.dmichalski.shoping_list.adapters.SpecialAdapter;
import pl.dmichalski.shoping_list.database.dao.ShopingListDao;
import pl.dmichalski.shoping_list.shoping.ShopingList;

public class AddAndEditDialog extends Dialog {
    private static final int EDIT = 1;
    private static final int ADD = 2;
    private int chose;
    private ShopingList selectedItem;

    private ShopingListDao shopingListDao;

    private EditText listNameEitText;

    private SpecialAdapter<ShopingList> adapter;

    public AddAndEditDialog(Context context, SpecialAdapter<ShopingList> adapter, boolean isEdit, ShopingList selectedItem) {
        super(context);
        setContentView(R.layout.add_and_edit_list);

        this.selectedItem = selectedItem;

        shopingListDao = new ShopingListDao(context);

        this.adapter = adapter;

        listNameEitText = (EditText) findViewById(R.id.listNameEditText);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new ButtonSaveClickListener());

        if (isEdit) chose = EDIT;
        else chose = ADD;

        prepareDialog();
    }

    private void prepareDialog() {
        if (chose == EDIT) {
            listNameEitText.setText(selectedItem.getName());
            Toast.makeText(getContext(), selectedItem.getName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        listNameEitText.setText("");
    }

    private class ButtonSaveClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (chose) {
                case ADD:
                    ShopingList shopingList = new ShopingList(listNameEitText.getText().toString());
                    shopingListDao.save(shopingList);
                    adapter.addItem(shopingList);
                    listNameEitText.setText("");
                    dismiss();
                case EDIT:
                    // kod edit
                    break;
            }
        }
    }
}
