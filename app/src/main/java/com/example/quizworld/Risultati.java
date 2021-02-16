package com.example.quizworld;

public class Risultati {

    private int id;
    private String utente;
    private String lingua;
    private String argomento;
    private int totali;
    private int corrette;

    public Risultati(){}

    public Risultati(String utente, String lingua, String argomento, int totali, int corrette) {
        super();
        this.utente = utente;
        this.lingua = lingua;
        this.argomento = argomento;
        this.totali = totali;
        this.corrette = corrette;
    }


    //getters & setters
    public int getId() {
        return id;
    }
    public String getUtente() {
        return utente;
    }

    public String getLingua() {
        return lingua;
    }

    public String getArgomento() {
        return argomento;
    }

    public int getTotali() {
        return totali;
    }

    public int getCorrette() {
        return corrette;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public void setTotali(int totali) {
        this.totali = totali;
    }

    public void setCorrette(int corrette) {
        this.corrette = corrette;
    }

    @Override
    public String toString() {
        return "Risultati{" +
                "Id='" + id + '\'' +
                ", Utente='" + utente + '\'' +
                ", Lingua='" + lingua + '\'' +
                ", Argomento='" + argomento + '\'' +
                ", Totali='" + totali + '\'' +
                ", Corrette='" + corrette + '\'' +
                '}';
    }
}
