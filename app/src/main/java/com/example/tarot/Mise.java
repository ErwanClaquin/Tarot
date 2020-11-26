package com.example.tarot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Mise extends AppCompatActivity {
    private String miseur;
    private int mise;
    private int petit = 0;
    private int poignee = 0;
    private LinearLayout linearLayoutPlayer;
    private LinearLayout linearLayoutMises;
    private LinearLayout linearLayoutPetit;
    private LinearLayout linearLayoutPoignee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mise);

        Intent intent = getIntent();
        ArrayList<String> players = intent.getStringArrayListExtra("players");

        //Players
        assert (players != null);
        linearLayoutPlayer = findViewById(R.id.players);
        for (String player : players) {
            Button button = new Button(this);
            button.setText(player);
            button.setOnClickListener(v -> {
                updatePlayer(player);
                updateView(button, linearLayoutPlayer);
            });
            linearLayoutPlayer.addView(button);
        }

        //Mises
        linearLayoutMises = findViewById(R.id.mises);
        final ArrayList<String> mises = new ArrayList<>(Arrays.asList("Prise", "Garde", "Garde sans", "Garde contre"));
        for (String miseFor : mises) {
            Button button = new Button(this);
            button.setText(miseFor);
            button.setOnClickListener(v -> {
                updateMise(miseFor);
                updateView(button, linearLayoutMises);
            });
            linearLayoutMises.addView(button);
        }

        //Petit
        linearLayoutPetit = findViewById(R.id.petit);
        final ArrayList<String> small = new ArrayList<>(Arrays.asList("Petit réussi", "Petit raté", "Pas de petit"));
        for (String p : small) {
            Button button = new Button(this);
            button.setText(p);
            button.setOnClickListener(v -> {
                updatePetit(p);
                updateView(button, linearLayoutPetit);
            });
            linearLayoutPetit.addView(button);
        }
        //Petit
        linearLayoutPoignee = findViewById(R.id.poignee);
        final ArrayList<String> grasp = new ArrayList<>(Arrays.asList("Poignée simple", "Poignée double", "Poignée triple", "Pas de poignée"));
        for (String p : grasp) {
            Button button = new Button(this);
            button.setText(p);
            button.setOnClickListener(v -> {
                updatePoignee(p);
                updateView(button, linearLayoutPoignee);
            });
            linearLayoutPoignee.addView(button);
        }

        //valider
        Button button = findViewById(R.id.valider);
        button.setOnClickListener(v -> valider());
    }

    private void updatePlayer(String player) {
        miseur = player;
    }

    private void updateMise(String miseParam) {
        switch (miseParam) {
            case "Prise":
                mise = 1;
                break;
            case "Garde":
                mise = 2;
                break;
            case "Garde sans":
                mise = 4;
                break;
            case "Garde contre":
                mise = 6;
                break;
        }

    }

    private void updatePetit(String p) {
        switch (p) {
            case "Petit réussi":
                petit = 10;
                break;
            case "Petit raté":
                petit = -10;
                break;
            default:
                petit = 0;
                break;
        }
    }

    private void updatePoignee(String p) {
        switch (p) {
            case "Poignée simple":
                poignee = 20;
                break;
            case "Poignée double":
                poignee = 30;
                break;
            case "Poignée triple":
                poignee = 40;
                break;
            default:
                poignee = 0;
                break;

        }

    }

    private void updateView(Button b, LinearLayout ln) {
        for (int i = 0; i < ln.getChildCount(); i++) {
            Button button = (Button) ln.getChildAt(i);
            button.setAlpha(1f);
        }
        b.setAlpha(0.40f);
    }

    private void valider() {
        EditText editText = findViewById(R.id.points);
        String text = editText.getText().toString();
        boolean badFormat = false;
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException err) {
            badFormat = true;
        }
        if (badFormat) {
            Toast.makeText(getApplicationContext(), "Mauvais format !", Toast.LENGTH_LONG).show();
        } else {
            if (text.equals("") || miseur.equals("") || mise == 0) {
                Toast.makeText(getApplicationContext(), "Erreur, argument(s) manquant(s).", Toast.LENGTH_LONG).show();
            } else if (Integer.parseInt(text) > 91 || Integer.parseInt(text) < -91) {
                Toast.makeText(getApplicationContext(), "Mauvais points.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("miseur", miseur);
                intent.putExtra("typeMise", mise);
                intent.putExtra("points", Integer.parseInt(text));
                intent.putExtra("petit", petit);
                intent.putExtra("poignee", poignee);
                setResult(RESULT_OK, intent);
                finish();
            }
        }


    }
}