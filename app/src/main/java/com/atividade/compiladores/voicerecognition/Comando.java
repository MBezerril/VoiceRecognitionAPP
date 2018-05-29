package com.atividade.compiladores.voicerecognition;

import java.util.ArrayList;
import java.util.Arrays;

public class Comando {
    private String comando;
    private ItemLista item;
    private ArrayList<String> entrada;
    private ArrayList<String> tokens;
    private ArrayList<String> classificacao;
    private ArrayList<String> acoes = new ArrayList<String>(Arrays.asList("Inserir", " insira", " adicionar", " adicione", " colocar", " coloque", " incluir", " inclua", "Excluir",
            " exclua", " deletar", " delete", " apagar", " apague", " remover", " remova", " exterminar", " extermine", "Buscar", " busque",
            " pesquisar", " pesquise", " procurar", " procure", " achar", " ache", " Marcar", " marque"));
    private ArrayList<String> artigos = new ArrayList<String>(Arrays.asList("o", "a"));
    private ArrayList<String> numeros = new ArrayList<String>(Arrays.asList("um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "catorze", "quinze", "dezesseis", "dezessete", "dezoito",
            "dezenove", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta",
            "noventa", "cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos", "mil"));

    private ArrayList<String> expressao = new ArrayList<String>(Arrays.asList("para", "mim", "por", "favor"));
    int posicao = 0;

    //Constructor
    public Comando(String entrada) {
        this.entrada = new ArrayList<String>(Arrays.asList(entrada.split("\\s+")));
    }

    private String getSimbolo() {
        return tokens.get(posicao);
    }

    private String getClassificacao() {
        return classificacao.get(posicao);
    }

    private boolean AnalisadorLexico() {
        for (int i = 0; i < entrada.size(); i++) {
            if (acoes.contains(entrada.get(i))) {
                tokens.add(entrada.get(i).toLowerCase());
                classificacao.add("Acao");
            } else if (artigos.contains(entrada.get(i))) {
                tokens.add(entrada.get(i).toLowerCase());
                classificacao.add("Artigo");
            } else if (numeros.contains(entrada.get(i))) {
                tokens.add(entrada.get(i).toLowerCase());
                classificacao.add("Numero");
            } else if (expressao.contains(entrada.get(i))) {
                if (entrada.get(i).equals("para") && entrada.get(i + 1).equals("mim")) {
                    tokens.add(entrada.get(i).toLowerCase() + " " + entrada.get(i + 1).toLowerCase());
                    classificacao.add("Expressao");
                    i++;
                } else if (entrada.get(i).equals("por") && entrada.get(i + 1).equals("favor")) {
                    tokens.add(entrada.get(i).toLowerCase() + " " + entrada.get(i + 1).toLowerCase());
                    classificacao.add("Expressao");
                    i++;
                }
            } else {
                tokens.add(entrada.get(i).toLowerCase());
                classificacao.add("Produto");
            }
        }
        return true;
    }

    private boolean AnalisadorSintaticoSintatico() {
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
            return true;
        } else {
            return false;
        }
    }

    private boolean Acao() {
        if (!getClassificacao().equals("Acao"))
            return false;
        switch (getSimbolo()) {
            case "inserir":
            case "insira":
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
            default:
                return false;
        }
        return true;
    }

    private void Artigo() {

    }

    private boolean Produto() {
        return false;
    }

    private void Quantidade() {
        if (!getClassificacao().equals("Quantidade"))
            return;
        switch (getSimbolo()) {
            case "um":
                item.setQuantidade(1);
                break;
            case "dois":
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
        posicao++;
    }


    private void Expressao() {
        if (getClassificacao().equals("Expressao"))
            posicao++;
    }

    public String[] getComando() {
        return null;
    }

    public ItemLista getItem() {
        return null;
    }
}
