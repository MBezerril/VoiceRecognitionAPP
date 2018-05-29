package com.atividade.compiladores.voicerecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VoiceRecognition extends AppCompatActivity {

    private ListView lvLista;
    private EditText etInputText;
    private List<ItemLista> lista;
    private ItemListaAdapter iadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);
        lvLista = (ListView) findViewById(R.id.lvLista);
        etInputText = (EditText) findViewById(R.id.inputCommand);
        lista = new ArrayList<>();
        lista.add(new ItemLista("Macarrao",10));
        lista.add(new ItemLista("ovo",12));
        lista.add(new ItemLista("Ford Focus",2));
        iadpt =  new ItemListaAdapter(getApplicationContext(),lista);
        lvLista.setAdapter(iadpt);
    }

    public void getText(View view){
        String nome = etInputText.getText().toString();
        lista.add(new ItemLista(nome,1));
        iadpt.notifyDataSetChanged();
    }
}
