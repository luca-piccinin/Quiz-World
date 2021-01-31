package com.example.quizworld;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private String utente;
    private boolean flag;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        AssetManager assetManager = getAssets();
        Spinner Argomenti = (Spinner) findViewById(R.id.sprArgomenti);

        Intent t =  getIntent();
        if(t.hasExtra("Utente"))
            utente = getIntent().getStringExtra("Utente");
        if(t.hasExtra("Lingua"))
            flag = getIntent().getBooleanExtra("Lingua",true);

        TextView view5 = (TextView) findViewById(R.id.view5);
        TextView view6 = (TextView) findViewById(R.id.view6);
        TextView view7 = (TextView) findViewById(R.id.view7);
        Button Continua = (Button) findViewById(R.id.btnContinua);

        if(flag) {
            view5.setText("Select");
            view6.setText("the");
            view7.setText("Argument");
            Continua.setText("Start the Quiz");
        }
        else {
            view5.setText("Scelta");
            view6.setVisibility(View.INVISIBLE);
            view7.setText("Argomento");
            Continua.setText("Inizia il Quiz");
        }
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_spinner_item, leggiFile());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Argomenti.setAdapter(adapter);*/
        Argomenti.setAdapter(new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, leggiFile()));


        Continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(SecondActivity.this, ThirdActivity.class);
                t.putExtra("Utente",utente.toString());
                t.putExtra("Argomento",Argomenti.getSelectedItem().toString());
                t.putExtra("Lingua",flag);
                startActivity(t);
            }
        });
    }

    public List<String> leggiFile(){

        BufferedReader reader = null;
        String temp;
        List<String> a = new ArrayList<String>();
        try {
            if(flag)
                reader = new BufferedReader(new InputStreamReader(getAssets().open("Argomenti_ENG.txt"), "UTF-8"));
            else
                reader = new BufferedReader(new InputStreamReader(getAssets().open("Argomenti.txt"), "UTF-8"));
            while ((temp = reader.readLine()) != null) {
                a.add(temp.toString());
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
        return a;
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