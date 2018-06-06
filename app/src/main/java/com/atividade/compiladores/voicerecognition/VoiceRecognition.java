package com.atividade.compiladores.voicerecognition;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VoiceRecognition extends AppCompatActivity {
    // ESSE É O "MAIN" DO PROJETO
    //Esse é o primeiro código a ser executado quando se abre o App

    private final int REQUEST_SPEECH_RECOGNIZER = 3000;
    private final String mQuestion = "Fale o comando.";
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
        lista = new ArrayList<ItemLista>();
        lista.add(new ItemLista("Macarrao", 10));
        lista.add(new ItemLista("ovo", 12));
        lista.add(new ItemLista("Ford Focus", 2));
        iadpt = new ItemListaAdapter(getApplicationContext(), lista);
        lvLista.setAdapter(iadpt);
        lvLista.setFocusableInTouchMode(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            if (resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra
                        (RecognizerIntent.EXTRA_RESULTS);
                String entrada = results.get(0);

                Comando cmd = new Comando(entrada);
                if (cmd.Processa()) {
                    if (cmd.getComando().equals("incluir")) {
                        Iterator<ItemLista> iter = lista.iterator();
                        boolean found = false;
                        String nome = cmd.getItem().getNome();

                        if (nome.endsWith("s ")) {
                            nome = nome.substring(0, nome.length() - 2);
                        } else {
                            nome = nome.substring(0, nome.length() - 1);
                        }

                        while(iter.hasNext()) {
                            ItemLista item = iter.next();
                            if (item.getNome().equals(nome)) {
                                item.setQuantidade(item.getQuantidade() + cmd.getItem().getQuantidade());
                                found = true;
                            }
                        }

                        if (!found) {
                            lista.add(new ItemLista(nome, cmd.getItem().getQuantidade()));
                        }
                        Toast.makeText(getApplicationContext(), cmd.getItem().getQuantidade() + " " + nome + "(s) inserido(a) da lista", Toast.LENGTH_SHORT).show();
                    }
                    else if (cmd.getComando().equals("excluir")) {
                        Iterator<ItemLista> iter = lista.iterator();
                        boolean found = false;
                        String nome = cmd.getItem().getNome();

                        if (nome.endsWith("s ")) {
                            nome = nome.substring(0, nome.length() - 2);
                        } else {
                            nome = nome.substring(0, nome.length() - 1);
                        }

                        while(iter.hasNext()) {
                            ItemLista item = iter.next();
                            if (item.getNome().equals(nome)) {
                                found = true;

                                if (item.getQuantidade() <=  cmd.getItem().getQuantidade()) {
                                    iter.remove();
                                } else {
                                    item.setQuantidade(item.getQuantidade() - cmd.getItem().getQuantidade());
                                }

                                Toast.makeText(getApplicationContext(), cmd.getItem().getQuantidade() + " " + nome + "(s) removido(a) da lista", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                        if (!found) {
                            Toast.makeText(getApplicationContext(), "item " + nome + " nao esta na lista", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (cmd.getComando().equals("buscar")) {
                        Iterator<ItemLista> iter = lista.iterator();
                        boolean found = false;
                        String nome = cmd.getItem().getNome();

                        if (nome.endsWith("s ")) {
                            nome = nome.substring(0, nome.length() - 2);
                        } else {
                            nome = nome.substring(0, nome.length() - 1);
                        }

                        while(iter.hasNext()) {
                            ItemLista item = iter.next();
                            if (item.getNome().equals(nome)) {
                                Toast.makeText(getApplicationContext(), item.getQuantidade() + " " + nome + "(s) estao na lista", Toast.LENGTH_SHORT).show();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            Toast.makeText(getApplicationContext(), "item " + nome + " nao esta na lista", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (cmd.getComando().equals("marcar")) {
                        Iterator<ItemLista> iter = lista.iterator();
                        boolean found = false;
                        String nome = cmd.getItem().getNome();

                        if (nome.endsWith("s ")) {
                            nome = nome.substring(0, nome.length() - 2);
                        } else {
                            nome = nome.substring(0, nome.length() - 1);
                        }

                        while(iter.hasNext()) {
                            ItemLista item = iter.next();
                            if (item.getNome().equals(nome)) {
                                int position = lista.indexOf(item);
                                lvLista.setSelection(position);
                                lvLista.smoothScrollToPosition(position);
                                found = true;
                                Toast.makeText(getApplicationContext(), nome + "(s) marcado", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                        if (!found) {
                            Toast.makeText(getApplicationContext(), "item " + nome + " nao esta na lista", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Comando invalido", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Comando invalido", Toast.LENGTH_SHORT).show();
            }
        }

        iadpt.notifyDataSetChanged();
    }

    private void startSpeechRecognizer() {
        Intent intent = new Intent
                (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mQuestion);
        startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
    }


    public void getText(View view) {
        startSpeechRecognizer();
    }
}
