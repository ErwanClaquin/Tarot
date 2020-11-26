package com.example.tarot;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Game extends AppCompatActivity {
    private int numberPlayer;
    private TableLayout tableLayout;
    private ArrayList<String> players;
    final private int requestCodeMise = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        players = intent.getStringArrayListExtra("players");
        assert (players != null);
        numberPlayer = players.size();
        tableLayout = findViewById(R.id.mainTable);
        TableRow firstTableRow = (TableRow) tableLayout.getChildAt(0);
        if (numberPlayer == 3) {
            firstTableRow.removeView(firstTableRow.getChildAt(3));
        }
        for (int i = 0; i < players.size(); i++) {
            TextView textView = (TextView) firstTableRow.getChildAt(i);
            textView.setText(players.get(i));
        }

        Button buttonNew = findViewById(R.id.buttonAddMise);
        buttonNew.setOnClickListener(v -> newMise());

    }

    private void newMise() {
        Intent intent = new Intent(this, Mise.class);
        intent.putStringArrayListExtra("players", players);
        startActivityForResult(intent, requestCodeMise);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeMise) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final String miseur = data.getStringExtra("miseur");
                    final int points = data.getIntExtra("points", 0);

                    final int petit = data.getIntExtra("petit", 0);
                    String petitString = "";
                    switch (petit) {
                        case 10:
                            petitString = "\n(Petit réussi)";
                            break;
                        case -10:
                            petitString = "\n(Petit raté)";
                            break;
                        default:
                            break;
                    }

                    final int poignee = data.getIntExtra("poignee", 0);
                    String poigneeString = "";
                    switch (poignee) {
                        case 20:
                            poigneeString = "\n(Poignée simple)";
                            break;
                        case 30:
                            poigneeString = "\n(Poignée double)";
                            break;
                        case 40:
                            poigneeString = "\n(Poignée triple)";
                            break;
                        default:
                            break;
                    }

                    final int typeMise = data.getIntExtra("typeMise", 0);
                    String typeMiseString;
                    switch (typeMise) {
                        case 1:
                            typeMiseString = "Prise";
                            break;
                        case 2:
                            typeMiseString = "Garde";
                            break;
                        case 4:
                            typeMiseString = "Garde sans";
                            break;
                        case 6:
                            typeMiseString = "Garde contre";
                            break;
                        default:
                            typeMiseString = "Error";

                    }
                    TableRow tableRow = new TableRow(this);
                    int height = 0;
                    final int paddingDp = 25;
                    final float density = this.getResources().getDisplayMetrics().density;
                    final int paddingPixel = (int) (paddingDp * density);
                    for (int numberplayer = 0; numberplayer < numberPlayer; numberplayer++) {
                        TextView textView = new TextView(this);
                        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        textView.setPadding(paddingPixel, paddingPixel / 2, paddingPixel, paddingPixel / 2);
                        String s;
                        if (players.get(numberplayer).equals(miseur)) {
                            if (points < 0) {
                                textView.setTextColor(Color.parseColor("#FF0000"));
                                int base = 0;
                                if (tableLayout.getChildCount() != 1) {
                                    TableRow tableRowPrev = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() - 1);
                                    TextView textViewPrev = (TextView) tableRowPrev.getChildAt(numberplayer);
                                    base = Integer.parseInt((String) textViewPrev.getText());
                                }
                                s = Integer.toString(base + (-poignee + (points - 25 + petit) * typeMise) * (numberPlayer - 1));

                            } else {
                                textView.setTextColor(Color.parseColor("#00FF00"));
                                int base = 0;
                                if (tableLayout.getChildCount() != 1) {
                                    TableRow tableRowPrev = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() - 1);
                                    TextView textViewPrev = (TextView) tableRowPrev.getChildAt(numberplayer);
                                    base = Integer.parseInt((String) textViewPrev.getText());
                                }
                                s = Integer.toString(base + (poignee + (points + 25 + petit) * typeMise) * (numberPlayer - 1));
                            }
                        } else {
                            if (points < 0) {
                                textView.setTextColor(Color.parseColor("#00FF00"));
                                int base = 0;
                                if (tableLayout.getChildCount() != 1) {
                                    TableRow tableRowPrev = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() - 1);
                                    TextView textViewPrev = (TextView) tableRowPrev.getChildAt(numberplayer);
                                    base = Integer.parseInt((String) textViewPrev.getText());
                                }
                                s = Integer.toString(base + poignee + (-points + 25 - petit) * typeMise);

                            } else {
                                textView.setTextColor(Color.parseColor("#FF0000"));
                                int base = 0;
                                if (tableLayout.getChildCount() != 1) {
                                    TableRow tableRowPrev = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() - 1);
                                    TextView textViewPrev = (TextView) tableRowPrev.getChildAt(numberplayer);
                                    base = Integer.parseInt((String) textViewPrev.getText());
                                }
                                s = Integer.toString(base + -poignee + (-points - 25 - petit) * typeMise);
                            }
                        }
                        textView.setText(s);
                        tableRow.addView(textView);
                    }


                    TextView textView = new TextView(this);
                    String s = typeMiseString + " : ";
                    if (points > 0) {
                        s += "+";
                    }
                    s += String.valueOf(points);

                    s += petitString;
                    s += poigneeString;

                    textView.setText(s);
                    textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
                    textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    textView.setPadding(paddingPixel, paddingPixel / 2, paddingPixel, paddingPixel / 2);
                    tableRow.addView(textView);
                    tableLayout.addView(tableRow);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Here you want to show the user a dialog box
        if (tableLayout.getChildCount() > 1) { //Mean already
            new AlertDialog.Builder(this)
                    .setTitle("Retour au menu")
                    .setMessage("Êtes-vous sûr ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // The user wants to leave - so dismiss the dialog and exit
                            finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // The user is not sure, so you can exit or just stay
                    dialog.dismiss();
                }
            }).show();
        } else {
            super.onBackPressed();
        }


    }
}