package com.example.quizworld;

import java.util.ArrayList;
import java.util.List;

public class Domande {
    private String domanda;

    public String getDomanda() {
        return domanda;
    }

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    private String risposta;

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public Domande() {
    }

    public Domande(String d, String r) {
        this.domanda = d;
        this.risposta = r;
    }
}
