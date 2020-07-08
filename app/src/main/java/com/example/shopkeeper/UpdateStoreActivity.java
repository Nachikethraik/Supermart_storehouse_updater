package com.example.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UpdateStoreActivity extends AppCompatActivity {
    MaterialEditText barcodenumber;
    Button updateitem, barcodescan;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcodenumber = findViewById(R.id.update_barcode);
        updateitem = findViewById(R.id.update);
        barcodescan = findViewById(R.id.scan_to_update);

        updateitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = barcodenumber.getText().toString();
                if(TextUtils.isEmpty(barcode))
                {
                    Toast.makeText(getApplicationContext(),"Invalid input",Toast.LENGTH_LONG).show();
                }
                else {
                    reference = FirebaseDatabase.getInstance().getReference("Items");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(barcode).exists()) {
                                reference.child(barcode).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            reference = FirebaseDatabase.getInstance().getReference("Items").child(barcode);
                                            Intent intent = new Intent(getBaseContext(), UpdateFormActivity.class);
                                            intent.putExtra("barcode", barcode);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Failed to update item", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
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
        barcodescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateStoreActivity.this,ScannedBarcodeActivity.class).putExtra("Class","UpdateFormActivity"));
            }
        });
    }
}
