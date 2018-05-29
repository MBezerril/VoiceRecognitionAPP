package com.atividade.compiladores.voicerecognition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemListaAdapter extends BaseAdapter {

    private Context context;
    private List<ItemLista> listaDeItens;

    //Constructor


    public ItemListaAdapter(Context Context, List<ItemLista> listaDeItens) {
        this.context = Context;
        this.listaDeItens = listaDeItens;
    }

    @Override
    public int getCount() {
        return listaDeItens.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeItens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_lista_view, null);
        TextView txvNome = (TextView) v.findViewById(R.id.txvNome);
        TextView txvQuantidade = (TextView) v.findViewById(R.id.txvQuantidade);

        txvNome.setText(listaDeItens.get(position).getNome());
        String quant = "x" + String.valueOf(listaDeItens.get(position).getQuantidade());
        txvQuantidade.setText(quant);

        return v;
    }
}
