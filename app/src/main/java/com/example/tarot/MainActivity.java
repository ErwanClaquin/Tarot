package com.example.tarot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout);
        Button buttonNew = findViewById(R.id.buttonStart);
        buttonNew.setOnClickListener(v -> checkStartConditions());
    }

    private void checkStartConditions() {
        ArrayList<String> players = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            EditText editText = (EditText) layout.getChildAt(i);
            if (!String.valueOf(editText.getText()).equals("")) {
                players.add(String.valueOf(editText.getText()));
            }
        }
        if (players.size() < 3) {
            Toast.makeText(getApplicationContext(), "Pas assez de joueurs", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(MainActivity.this, Game.class);
            intent.putStringArrayListExtra("players", players);
            startActivity(intent);
        }

    }
}