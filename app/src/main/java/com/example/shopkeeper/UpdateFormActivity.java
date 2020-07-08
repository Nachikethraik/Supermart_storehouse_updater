package com.example.shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopkeeper.Model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class UpdateFormActivity extends AppCompatActivity {
    TextView barcodenumber;
    MaterialTextView itemname;
    MaterialEditText itemmrp, itemdiscount, itemquantity;
    Button updateitem;
    ProgressBar progress;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Store");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcodenumber = findViewById(R.id.barcode_update);
        itemname = findViewById(R.id.item_name_update);
        itemmrp = findViewById(R.id.mrp_update);
        itemdiscount = findViewById(R.id.discount_update);
        itemquantity = findViewById(R.id.quantity_update);
        updateitem = findViewById(R.id.updateitem_store);
        progress= findViewById(R.id.progressbar_update);

        String barcodestring = getIntent().getStringExtra("barcode");
        barcodenumber.setText(barcodestring);
        reference = FirebaseDatabase.getInstance().getReference("Items").child(barcodestring);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);
                    itemname.setText(item.getName());
                    itemmrp.setText(item.getMRP());
                    itemdiscount.setText(item.getDiscount());
                    itemquantity.setText(item.getQuantity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        updateitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalprice;
                progress.setVisibility(View.VISIBLE);
                String item_mrp = itemmrp.getText().toString();
                String item_discount = itemdiscount.getText().toString();
                String item_quantity = itemquantity.getText().toString();
                finalprice = getFinalPrice(item_mrp,item_discount);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", barcodestring);
                hashMap.put("MRP", item_mrp);
                hashMap.put("Discount",item_discount);
                hashMap.put("Quantity",item_quantity);
                hashMap.put("finalprice",finalprice);
                reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Item updated Successfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateFormActivity.this,MainActivity.class));
                            finish();
                        }
                        else
                        {
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Failed to update item",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    private String getFinalPrice(String item_mrp, String item_discount)
    {
        int mrp=Integer.parseInt(item_mrp);
        int discount = Integer.parseInt(item_discount);
        int finalprice = (int)(mrp-(mrp*discount/100));
        return Integer.toString(finalprice);
    }
}
