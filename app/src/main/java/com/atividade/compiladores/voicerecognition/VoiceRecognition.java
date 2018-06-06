package com.atividade.compiladores.voicerecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VoiceRecognition extends AppCompatActivity {
    // ESSE É O "MAIN" DO PROJETO
    //Esse é o primeiro código a ser executado quando se abre o App

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
        lista.add(new ItemLista("Macarrao", 10));
        lista.add(new ItemLista("ovo", 12));
        ItemLista it = new ItemLista("Ford Focus", 2);
        it.setMarcado(true);
        lista.add(it);
        iadpt = new ItemListaAdapter(getApplicationContext(), lista);
        lvLista.setAdapter(iadpt);
    }

    public void getText(View view) {
        Comando cmd;
        String entrada = etInputText.getText().toString();
        cmd = new Comando(entrada);
        if (cmd.Processa()) {
            if (cmd.getComando().equals("incluir")) {
                lista.add(cmd.getItem());
            }
            else if (cmd.getComando().equals("excluir")) {
                lista.remove(cmd.getItem());
            }
        }
        else
            Toast.makeText(getApplicationContext(), "FALHA", Toast.LENGTH_SHORT).show();
        iadpt.notifyDataSetChanged();
    }
}
