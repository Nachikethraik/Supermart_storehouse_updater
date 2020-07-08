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

public class AddItemActivity extends AppCompatActivity {
    MaterialEditText barcodenumber;
    Button additem, barcodescan;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcodenumber = findViewById(R.id.add_barcode);
        additem = findViewById(R.id.add_item);
        barcodescan = findViewById(R.id.scan_to_add);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String barcode = barcodenumber.getText().toString();
                if(TextUtils.isEmpty(barcode))
                {
                    Toast.makeText(getApplicationContext(),"Invalid input",Toast.LENGTH_LONG).show();
                }
                else
                {
                    reference = FirebaseDatabase.getInstance().getReference("Items");
                    reference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(barcode).exists()) {
                                Toast.makeText(AddItemActivity.this, "Item already exist", Toast.LENGTH_LONG).show();
                            } else {
                                reference = FirebaseDatabase.getInstance().getReference("Items").child(barcode);
                                Intent intent = new Intent(getBaseContext(), ProductDescriptionActivity.class);
                                intent.putExtra("barcode", barcode);
                                startActivity(intent);
                                finish();
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
                startActivity(new Intent(AddItemActivity.this,ScannedBarcodeActivity.class).putExtra("Class","ProductDescriptionActivity"));
            }
        });
    }
}
