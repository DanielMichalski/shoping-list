package pl.dmichalski.shoping_list.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.lista_zakupow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter ktory używa lista z zakupami/ produktami
 *
 * @param <T> element listy
 */
public class SpecialAdapter<T extends GetterName> extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<T> data;
    private List<Boolean> selected;

    /**
     * Konstruktor klasy SpecialAdapter
     *
     * @param context      obecny kontekst
     * @param listaZakupow listaZZakupami
     */
    public SpecialAdapter(Context context, List<T> listaZakupow) {
        mInflater = LayoutInflater.from(context);
        this.data = listaZakupow;
        selected = new ArrayList<Boolean>();
        for (int i = 0; i < listaZakupow.size(); i++)
            selected.add(false);
    }

    /**
     * Zwraca ilosc elementow listy
     *
     * @return ilosc elementow listy
     */
    public int getCount() {
        return data.size();
    }

    /**
     * Zwraca element listy
     *
     * @param i pozycja
     * @return element listy
     */
    public T getItem(int i) {
        return data.get(i);
    }

    /**
     * Usuwa element
     *
     * @param position pozycja usuwaneo elementu
     */
    public void removeItem(int position) {
        data.remove(position);
        selected.remove(position);
        notifyDataSetChanged();
    }

    /**
     * Dodaje element do listy
     *
     * @param item element do dodania
     */
    public void addItem(T item) {
        this.data.add(item);
        this.selected.add(false);
        notifyDataSetChanged();
    }

    /**
     * Zwraca id elementu
     *
     * @param position pozycja szukanego elementu
     * @return id elementu
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Ustawia zaznaczenie produktu
     *
     * @param pos pozycja elementu do zaznaczenia
     */
    public void setSelected(int pos) {
        unselectAll();
        this.selected.set(pos, true);
        notifyDataSetChanged();
    }

    /**
     * Odznaczenie wszystkich elementow
     */
    public void unselectAll() {
        for (int i = 0; i < selected.size(); i++)
            this.selected.set(i, false);
        notifyDataSetChanged();
    }

    /**
     * Odznaczenie elementu
     *
     * @param pos pozycja elementu do odznaczenia
     */
    public void unselect(int pos) {
        this.selected.set(pos, false);
        notifyDataSetChanged();
    }

    /**
     * Czy jest jakis element na liscie zaznaczony
     *
     * @return true or false w zaleznosci czy jais element jest zaznaczony
     */
    public boolean isAnySelected() {
        for (Boolean selectedVal : selected) {
            if (selectedVal)
                return true;
        }
        return false;
    }

    /**
     * Zwraca widok elementu listy
     *
     * @param position    pozycja
     * @param convertView widok
     * @param parent      ojciec
     * @return widok
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.headline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(data.get(position).getNameToView());
        convertView.setBackgroundColor(Color.BLACK);

        if (selected.get(position)) {
            convertView.setBackgroundColor(Color.CYAN);
        }

        return convertView;
    }

    /**
     * zwraca wybrany element
     *
     * @return wybrany element
     */
    public T getSelectedItem() {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i))
                return data.get(i);
        }
        return null;
    }

    /**
     * Zwraca pozycję wybranego elementu
     *
     * @return pozycja wybranego elementu
     */
    public int getSelectedItemPos() {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i))
                return i;
        }
        return -1;
    }

    /**
     * Widok ktory jest widoczny na liscie
     */
    public static class ViewHolder {
        TextView text;
    }
}





