package com.example.quizworld;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private List<Domande> Quiz = new ArrayList<Domande>();
    private boolean flag;

    public Quiz(boolean flag) {
        this.flag = flag;
    }

    public Quiz(Domande d) {
        Quiz.add(d);
    }

    public void Caricamento(String argomento, Context myContext) {
        AssetManager assetManager = myContext.getAssets();
        BufferedReader reader = null;
        String dom, ris;
        try {
            reader = new BufferedReader(new InputStreamReader(assetManager.open(argomento + ".txt"), "UTF-8"));
            while ((dom = reader.readLine()) != null) {
                ris = reader.readLine();
                Quiz.add(new Domande(dom,ris));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int count() {
        return Quiz.size();
    }

    public String domanda(int i) {
        return Quiz.get(i).getDomanda();
    }

    public String risposta(int i) {
        return Quiz.get(i).getRisposta();
    }
}