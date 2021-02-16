package com.example.quizworld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class FourthActivity extends AppCompatActivity {
    private String utente;
    private boolean flag;
    private String argomento;
    private int totali;
    private int correct;
    private boolean back = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        AssetManager assetManager = getAssets();
        Intent t =  getIntent();
        if(t.hasExtra("Utente"))
            utente = getIntent().getStringExtra("Utente");
        if(t.hasExtra("Lingua"))
            flag = getIntent().getBooleanExtra("Lingua",true);
        if(t.hasExtra("Argomento"))
            argomento = getIntent().getStringExtra("Argomento");
        if(t.hasExtra("Totali"))
            totali = getIntent().getIntExtra("Totali",0);
        if(t.hasExtra("Corrette"))
            correct = getIntent().getIntExtra("Corrette",0);

        MySQLite db = new MySQLite(this);

        // add Books
        if(!flag)
            db.addResult(new Risultati(utente,"Italiano",argomento,totali,correct));
        else
            db.addResult(new Risultati(utente,"Inglese",argomento,totali,correct));

        TextView titolo = (TextView) findViewById (R.id.viewTitle);
        TextView classifica = (TextView) findViewById(R.id.viewClassifica);
        Button Classifica = (Button) findViewById(R.id.btnClassifica);

        if(flag) {
            titolo.setText("Score");
            Classifica.setText("Score");
            classifica.setText("User: " + utente + "\n" + "Argument: " + argomento + "\n" + "Question: " + totali + "\n" + "Correct: " + correct + "\n\n");
        }
        else {
            titolo.setText("Risultato");
            Classifica.setText("Risultato");
            classifica.setText("Utente: " + utente + "\n" + "Argomento: " + argomento + "\n" + "Domande: " + totali + "\n" + "Corrette: " + correct + "\n\n");
        }

        Classifica.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(!back) {
                    if (flag) {
                        titolo.setText("Ranking");
                        for(int i = 0; i < db.getAllBooks().size(); i++)
                            classifica.setText(StampaRisultati(db.getAllBooks(),flag,i));
                        Classifica.setText("Return to Hub");
                    } else {
                        titolo.setText("Risultati");
                        for(int i = 0; i < db.getAllBooks().size(); i++)
                            classifica.setText(StampaRisultati(db.getAllBooks(),flag,i));
                        Classifica.setText("Ritorna alla pagina iniziale");
                    }
                    //classifica.setText(leggiFile());
                    back = true;
                }
                else {
                    Intent t = new Intent(FourthActivity.this, MainActivity.class);
                    startActivity(t);
                }
            }
        });
    }

    public String StampaRisultati (List<Risultati> list,boolean flag, int i) {
        Risultati aux = list.get(i);
        if (flag)
            return "User: " + aux.getUtente() + "\n" + "Argument: " + aux.getArgomento() + "Lenguage: " + aux.getLingua() + "\n" + "Question: " + aux.getTotali() + "\n" + "Correct: " + aux.getCorrette() + "\n\n";
        else
            return "Utente: " + aux.getUtente() + "\n" + "Argomento: " + aux.getArgomento() + "Lingua: " + aux.getLingua() + "\n" + "Domande: " + aux.getTotali() + "\n" + "Corrette: " + aux.getCorrette() + "\n\n";
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit?");
        if(flag)
            builder.setMessage("Sure to exit?");
        else
            builder.setMessage("Sicuro di voler uscire?");
        builder.setCancelable(true);
        if(flag)
            builder.setNeutralButton("Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int id) {
                            dialog.dismiss();
                            finish();
                        }
                    });
        else
            builder.setNeutralButton("Chiudi",
                    new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int id) {
                            dialog.dismiss();
                            finish();
                        }
                    });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
