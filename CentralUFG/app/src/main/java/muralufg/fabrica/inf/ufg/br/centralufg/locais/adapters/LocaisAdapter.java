package muralufg.fabrica.inf.ufg.br.centralufg.locais.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.model.Locais;

public class LocaisAdapter extends BaseAdapter {

    TextView tvLocaisNome;
    private List<Locais> listaDeLocais;
    private LayoutInflater layoutInflater;

    public LocaisAdapter(Context context, List<Locais> listaDeLocais) {
        this.listaDeLocais = listaDeLocais;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaDeLocais.size();
    }

    @Override
    public Locais getItem(int position) {
        return listaDeLocais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Locais locais = getItem(position);
        view = layoutInflater.inflate(R.layout.item_local, null);
        tvLocaisNome = (TextView) view.findViewById(R.id.tvLocaisNome);

        popularView(locais);

        return view;
    }

    private void popularView(Locais locais) {
        tvLocaisNome.setText(locais.getNome());
        tvLocaisNome.setTypeface(null, Typeface.BOLD_ITALIC);
        tvLocaisNome.setTextColor(Color.BLACK);
    }
}
