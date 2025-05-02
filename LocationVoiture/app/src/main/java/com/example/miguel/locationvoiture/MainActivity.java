package com.example.miguel.locationvoiture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    DBHelper dbHelper;
    Button btnAjouter, btnLister, btnSupprimer, btnModifier;
    EditText edtID, edtNomPrenom, edtMarque, edtNumeroVoiture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison des vues
        btnAjouter = (Button) findViewById(R.id.btnAjouter);
        btnLister = (Button) findViewById(R.id.btnLister);
        btnSupprimer = (Button) findViewById(R.id.btnSupprimer);
        btnModifier = (Button) findViewById(R.id.btnModifier);
        edtID = (EditText) findViewById(R.id.edtID);
        edtNomPrenom = (EditText) findViewById(R.id.edtNomPrenom);
        edtMarque = (EditText) findViewById(R.id.edtMarque);
        edtNumeroVoiture = (EditText) findViewById(R.id.edtNumeroVoiture);

        dbHelper = new DBHelper(this);

        // Bouton Ajouter
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                String nom = edtNomPrenom.getText().toString();
                String marque = edtMarque.getText().toString();
                String numero = edtNumeroVoiture.getText().toString();

                if (nom.isEmpty() || marque.isEmpty() || numero.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(dbHelper.ajouterVoiture(nom, marque, numero)) {
                    Toast.makeText(MainActivity.this, "Voiture ajoutée", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bouton Lister
        btnLister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dbHelper.listerVoitures();
                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Aucune voiture trouvée", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder builder = new StringBuilder();
                while(cursor.moveToNext()){
                    builder.append("ID: ").append(cursor.getInt(0))
                            .append(" Nom: ").append(cursor.getString(1))
                            .append(" Marque: ").append(cursor.getString(2))
                            .append(" Numéro: ").append(cursor.getString(3)).append("\n");
                }
                Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Bouton Supprimer
        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                String idStr = edtID.getText().toString();
                if (idStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer l'ID à supprimer", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(idStr);
                if(dbHelper.supprimerVoiture(id)) {
                    Toast.makeText(MainActivity.this, "Voiture supprimée", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bouton Modifier : passage à Activity2 pour modification
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                String idStr = edtID.getText().toString();
                if(idStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Veuillez entrer l'ID à modifier", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("id", idStr);
                startActivity(intent);
            }
        });
    }
}
