package com.pulsetech.stonepaperscissors;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {

    Button start;
    private TextInputLayout textField;
    private TextInputEditText inputField;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        textField = findViewById(R.id.textField);
        inputField = findViewById(R.id.inputField);
        start = findViewById(R.id.btnStart);

        // Firebase initialization


        mRef = FirebaseDatabase.getInstance().getReference();



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname = inputField.getText().toString().trim();

                if (!TextUtils.isEmpty(nickname)) {
                    checkUsernameExists(nickname);
                } else {
                    Toast.makeText(MainMenu.this, "Lütfen kullanıcı adını giriniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkUsernameExists(final String nickname) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(nickname)) {
                    Toast.makeText(MainMenu.this, "Bu kullanıcı adı zaten kullanılıyor, lütfen başka bir ad seçin.", Toast.LENGTH_SHORT).show();
                } else {
                    // Username is available, save it to database
                    mRef.child(nickname).setValue(true);
                    Intent startIntent = new Intent(MainMenu.this, MainActivity.class);
                    startIntent.putExtra("Nickname", nickname);
                    startActivity(startIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainMenu.this, "Veritabanı hatası: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}