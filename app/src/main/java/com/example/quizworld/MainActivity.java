package com.example.quizworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    static boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();

        Button Invio = (Button) findViewById(R.id.btnInvio);
        Invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent t = new Intent(MainActivity.this, SecondActivity.class);
            EditText utente = (EditText) findViewById (R.id.txtUtente);
            t.putExtra("Utente",utente.getText().toString());
            if(flag)
                t.putExtra("Lingua",true);
            else
                t.putExtra("Lingua",false);
            utente.setText("");
            startActivity(t);
            }
        });

        RadioButton inglese = (RadioButton) findViewById(R.id.rdnInglese);
        RadioButton italiano = (RadioButton) findViewById(R.id.rdnItaliano);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TextView view1 = (TextView) findViewById(R.id.view1);
                TextView view2 = (TextView) findViewById(R.id.view2);
                TextView view3 = (TextView) findViewById(R.id.view3);
                TextView view4 = (TextView) findViewById(R.id.view4);
                Button btn1 = (Button) findViewById(R.id.btnInvio);
                Button btn2 = (Button) findViewById(R.id.btnRegole);
                if(inglese.isChecked()) {
                    flag = true;
                    view1.setText("Welcome");
                    view2.setText("to");
                    view3.setText("Quiz World");
                    view4.setText("USERNAME");
                    btn1.setText("Continue");
                    btn2.setText("Rules");
                }
                if(italiano.isChecked()) {
                    flag = false;
                    view1.setText("Benvenuto");
                    view2.setText("nel");
                    view3.setText("Mondo dei Quiz");
                    view4.setText("NOME");
                    btn1.setText("Continua");
                    btn2.setText("Regole");
                }
            }
        });

        Button Regole = (Button) findViewById(R.id.btnRegole);
        Regole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                if(flag)
                    builder.setTitle("The Rule");
                else
                    builder.setTitle("Le Regole");
                builder.setMessage(leggiFile());
                builder.setCancelable(true);
                if(flag)
                    builder.setNeutralButton("Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick( DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                else
                    builder.setNeutralButton("Chiudi",
                            new DialogInterface.OnClickListener() {
                                public void onClick( DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public String leggiFile(){

        BufferedReader reader = null;
        String line = "", temp;
        try {
            if(flag)
                reader = new BufferedReader(new InputStreamReader(getAssets().open("Rules.txt"), "UTF-8"));
            else
                reader = new BufferedReader(new InputStreamReader(getAssets().open("Regole.txt"), "UTF-8"));
            while ((temp = reader.readLine()) != null) {
                line += "\n" + temp.toString();
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
        return line;
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