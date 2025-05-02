package com.example.miguel.locationvoiture;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends Activity {

    DBHelper dbHelper;
    EditText edtNom, edtMarque, edtNumero;
    Button btnModifier;
    int voitureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        // Liaison des vues
        edtNom = (EditText) findViewById(R.id.nom);
        edtMarque = (EditText) findViewById(R.id.marque);
        edtNumero = (EditText) findViewById(R.id.NumeroVoiture);
        btnModifier = (Button) findViewById(R.id.btnModifier);

        dbHelper = new DBHelper(this);

        // Récupération de l'ID passé depuis MainActivity
        Intent intent = getIntent();
        voitureId = Integer.parseInt(intent.getStringExtra("id"));

        // Charger les données existantes pour la voiture
        Cursor cursor = dbHelper.listerVoitures();
        boolean trouve = false;
        while (cursor.moveToNext()) {
            if (cursor.getInt(0) == voitureId) {
                edtNom.setText(cursor.getString(1));
                edtMarque.setText(cursor.getString(2));
                edtNumero.setText(cursor.getString(3));
                trouve = true;
                break;
            }
        }
        if (!trouve) {
            Toast.makeText(this, "Voiture introuvée", Toast.LENGTH_SHORT).show();
        }

        // Bouton Modifier pour enregistrer les modifications (sans lambda)
        btnModifier.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String nom = edtNom.getText().toString();
                String marque = edtMarque.getText().toString();
                String numero = edtNumero.getText().toString();

                if(dbHelper.modifierVoiture(voitureId, nom, marque, numero)) {
                    Toast.makeText(Activity2.this, "Modification réussie", Toast.LENGTH_SHORT).show();
                    finish(); // Retour à MainActivity
                } else {
                    Toast.makeText(Activity2.this, "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
