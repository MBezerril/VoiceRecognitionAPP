package com.atividade.compiladores.voicerecognition;

import java.util.ArrayList;
import java.util.Arrays;

public class Comando {
    private String comando;
    private ItemLista item;
    private boolean processado = false;
    private ArrayList<String> entrada;
    private ArrayList<String> tokens;
    private ArrayList<String> classificacao;
    private ArrayList<String> acoes = new ArrayList<String>(Arrays.asList("inserir", "insira", "adicionar", "adicione", "colocar", "coloque", "incluir", "inclua", "excluir",
            "exclua", "deletar", "delete", "apagar", "apague", "remover", "remova", "exterminar", "extermine", "buscar", "busque",
            "pesquisar", "pesquise", "procurar", "procure", "achar", "ache", "marcar", "marque"));
    private ArrayList<String> artigos = new ArrayList<String>(Arrays.asList("o", "a", "os", "as"));
    /**
     * Assumindo que todos os números já fazem parte do dicionário, serão adicionados os números por extenso
     * para completar todas as possibilidades de numeros serem passados para o comando
     */
    private ArrayList<String> numeros = new ArrayList<String>(Arrays.asList("um", "uma", "dois", "duas", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "catorze", "quinze", "dezesseis", "dezessete", "dezoito",
            "dezenove", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta",
            "noventa", "cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos", "mil"));

    private ArrayList<String> expressao = new ArrayList<String>(Arrays.asList("para", "mim", "por", "favor"));
    private int posicao;

    //Constructor
    public Comando(String entrada) {
        entrada = entrada.toLowerCase();
        this.entrada = new ArrayList<String>(Arrays.asList(entrada.split("\\s+")));
        this.tokens = new ArrayList<String>();
        this.item = new ItemLista("", 1);
        this.classificacao = new ArrayList<String>();
        this.comando = "";
        this.posicao = 0;
    }

    private String getSimbolo() {
        if (posicao < tokens.size())
            return tokens.get(posicao);
        else
            return "";
    }

    private String getClassificacao() {
        if (posicao < classificacao.size())
            return classificacao.get(posicao);
        else
            return "";
    }

    private boolean AnalisadorLexico() {
        for (int i = 0; i < entrada.size(); i++) {
            if (acoes.contains(entrada.get(i))) {
                tokens.add(entrada.get(i));
                classificacao.add("Acao");
            } else if (artigos.contains(entrada.get(i))) {
                tokens.add(entrada.get(i));
                classificacao.add("Artigo");
            } else if (numeros.contains(entrada.get(i)) || android.text.TextUtils.isDigitsOnly(entrada.get(i))) {
                tokens.add(entrada.get(i));
                classificacao.add("Numero");
            } else if (expressao.contains(entrada.get(i))) {
                if (entrada.get(i).equals("para") && entrada.get(i + 1).equals("mim")) {
                    tokens.add(entrada.get(i) + " " + entrada.get(i + 1));
                    classificacao.add("Expressao");
                    i++;
                } else if (entrada.get(i).equals("por") && entrada.get(i + 1).equals("favor")) {
                    tokens.add(entrada.get(i) + " " + entrada.get(i + 1));
                    classificacao.add("Expressao");
                    i++;
                } else {
                    tokens.add(entrada.get(i));
                    classificacao.add("Produto");
                }
            } else {
                tokens.add(entrada.get(i));
                classificacao.add("Produto");
            }
        }
        return true;
    }

    public boolean Processa() {
        if (processado) return true;
        //Analise Sintatica e Semantica
        //Ao final o Item está preenchido e o tipo de comando
        //caso tudo esteja correto no comando enviado
        if (AnalisadorLexico()) {
            Expressao();
            if (!Acao())
                return false;
            Expressao();
            Artigo();
            Quantidade();
            if (!Produto())
                return false;
            Expressao();
            if (posicao < tokens.size())
                return false;
            processado = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean Acao() {
        if (!getClassificacao().equals("Acao"))
            return false;
        switch (getSimbolo()) {
            case "insira":
            case "inserir":
            case "adicionar":
            case "adicione":
            case "colocar":
            case "coloque":
            case "incluir":
            case "inclua":
                comando = "incluir";
                break;
            case "excluir":
            case "exclua":
            case "deletar":
            case "delete":
            case "apagar":
            case "apague":
            case "remover":
            case "remova":
            case "exterminar":
            case "extermine":
                comando = "excluir";
                break;
            case "buscar":
            case "busque":
                comando = "buscar";
                break;
            case "marcar":
            case "marque":
                comando = "marcar";
                break;
            default:
                return false;
        }
        posicao++;
        return true;
    }

    private void Artigo() {
        if (getClassificacao().equals("Artigo"))
            posicao++;
    }

    private boolean Produto() {
        if (!getClassificacao().equals("Produto"))
            return false;
        StringBuilder nomeFinal = new StringBuilder();
        while (posicao < tokens.size() && getClassificacao().equals("Produto")) {
            nomeFinal.append(getSimbolo()).append(" ");
            posicao++;
        }
        item.setNome(nomeFinal.toString());
        return true;
    }

    private void Quantidade() {
        if (!getClassificacao().equals("Numero"))
            return;
        switch (getSimbolo()) {
            case "um":
            case "uma":
                item.setQuantidade(1);
                break;
            case "dois":
            case "duas":
                item.setQuantidade(2);
                break;
            case "três":
                item.setQuantidade(3);
                break;
            case "quatro":
                item.setQuantidade(4);
                break;
            case "cinco":
                item.setQuantidade(5);
                break;
            case "seis":
                item.setQuantidade(6);
                break;
            case "sete":
                item.setQuantidade(7);
                break;
            case "oito":
                item.setQuantidade(8);
                break;
            case "nove":
                item.setQuantidade(9);
                break;
            case "dez":
                item.setQuantidade(10);
                break;
            case "onze":
                item.setQuantidade(11);
                break;
            case "doze":
                item.setQuantidade(12);
                break;
            case "treze":
                item.setQuantidade(13);
                break;
            case "quatorze":
                item.setQuantidade(14);
                break;
            case "catorze":
                item.setQuantidade(14);
                break;
            case "quinze":
                item.setQuantidade(15);
                break;
            case "dezesseis":
                item.setQuantidade(16);
                break;
            case "dezessete":
                item.setQuantidade(17);
                break;
            case "dezoito":
                item.setQuantidade(18);
                break;
            case "dezenove":
                item.setQuantidade(19);
                break;
            case "vinte":
                item.setQuantidade(20);
                break;
            case "trinta":
                item.setQuantidade(30);
                break;
            case "quarenta":
                item.setQuantidade(40);
                break;
            case "cinquenta":
                item.setQuantidade(50);
                break;
            case "sessenta":
                item.setQuantidade(60);
                break;
            case "setenta":
                item.setQuantidade(70);
                break;
            case "oitenta":
                item.setQuantidade(80);
                break;
            case "noventa":
                item.setQuantidade(90);
                break;
            case "cem":
                item.setQuantidade(100);
                break;
            default:
                item.setQuantidade(1);
                break;
        }
        if (android.text.TextUtils.isDigitsOnly(getSimbolo())) {
            item.setQuantidade(Integer.parseInt(getSimbolo()));
        }
        posicao++;
    }


    private void Expressao() {
        if (getClassificacao().equals("Expressao"))
            posicao++;
    }

    public String getComando() {
        return comando;
    }

    public ItemLista getItem() {
        return item;
    }

}
