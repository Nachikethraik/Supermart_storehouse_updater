package com.example.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class DeleteItemActivity extends AppCompatActivity {
    MaterialEditText barcode;
    Button deletebutton;
    ProgressBar delete_progress;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        barcode = findViewById(R.id.delete_barcode);
        deletebutton = findViewById(R.id.delete_item);
        delete_progress = findViewById(R.id.deleteprogress);
        reference = FirebaseDatabase.getInstance().getReference("Items");

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcodedata = barcode.getText().toString();
                if(TextUtils.isEmpty(barcodedata)) {
                    Toast.makeText(getApplicationContext(),"Invalid input",Toast.LENGTH_LONG).show();
                }
                else
                {
                    delete_progress.setVisibility(View.VISIBLE);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(barcodedata).exists()) {
                                reference.child(barcodedata).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Item Deleted Successfully", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(DeleteItemActivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            delete_progress.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), "Failed to delete item", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                delete_progress.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Item not found", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}
