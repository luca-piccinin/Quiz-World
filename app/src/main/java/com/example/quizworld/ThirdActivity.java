package com.example.quizworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    private String utente;
    private boolean flag;
    private String argomento;
    private int pointer = 0;
    private int correct = 0;
    private int n;
    private Quiz myQuiz;
    EditText risposta;
    TextView domanda;
    ProgressBar progessi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent t =  getIntent();
        if(t.hasExtra("Utente"))
            utente = getIntent().getStringExtra("Utente");
        if(t.hasExtra("Lingua"))
            flag = getIntent().getBooleanExtra("Lingua",true);
        if(t.hasExtra("Argomento"))
            argomento = getIntent().getStringExtra("Argomento");
        progessi = (ProgressBar) findViewById(R.id.Progressi);
        progessi.setProgress(0);

        myQuiz = new Quiz(flag);
        risposta = (EditText) findViewById (R.id.txtRisposta);
        domanda = (TextView) findViewById(R.id.viewDomanda);
        Button Send = (Button) findViewById(R.id.btnSend);
        myQuiz.Caricamento(argomento,ThirdActivity.this);
        n = myQuiz.count();

        if(flag)
            Send.setText("Send");
        else
            Send.setText("Invia");
        Esecuzione();

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ris = risposta.getText().toString().toUpperCase();
                if(ris.equals(myQuiz.risposta(pointer))) {
                    correct ++;
                }
                pointer ++;
                Esecuzione();
            }
        });
    }

    public void Avanzamento() {
        int x = 100 / (n - 1);
        progessi.setProgress(progessi.getProgress() + x);
    }

    private void Esecuzione() {
        if(pointer<n) {
            domanda.setText(myQuiz.domanda(pointer));
            risposta.setText("");
            Avanzamento();
        }
        else {
            Intent t = new Intent(ThirdActivity.this, FourthActivity.class);
            t.putExtra("Utente",utente.toString());
            t.putExtra("Argomento",argomento);
            t.putExtra("Lingua",flag);
            t.putExtra("Corrette",correct);
            t.putExtra("Totali",n);
            startActivity(t);
        }
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
