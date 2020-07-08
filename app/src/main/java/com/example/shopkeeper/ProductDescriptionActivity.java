package com.example.shopkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class ProductDescriptionActivity extends AppCompatActivity {
    TextView barcodenumber;
    MaterialEditText itemname, itemmrp, itemdiscount, itemquantity;
    Button additem;
    ProgressBar progress;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcodenumber = findViewById(R.id.barcode_add);
        itemname = findViewById(R.id.item_name);
        itemmrp = findViewById(R.id.mrp_add);
        itemdiscount = findViewById(R.id.discount_add);
        itemquantity = findViewById(R.id.quantity_add);
        additem = findViewById(R.id.add_item_to_store);
        progress= findViewById(R.id.progressbar);

        String barcodestring = getIntent().getStringExtra("barcode");
        barcodenumber.setText(barcodestring);
        reference = FirebaseDatabase.getInstance().getReference("Items").child(barcodestring);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalprice;
                progress.setVisibility(View.VISIBLE);
                String item_name = itemname.getText().toString();
                String item_mrp = itemmrp.getText().toString();
                String item_discount = itemdiscount.getText().toString();
                String item_quantity = itemquantity.getText().toString();
                finalprice = getFinalPrice(item_mrp,item_discount);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", barcodestring);
                hashMap.put("Name",item_name);
                hashMap.put("MRP", item_mrp);
                hashMap.put("Discount",item_discount);
                hashMap.put("Quantity",item_quantity);
                hashMap.put("search",item_name.toLowerCase());
                hashMap.put("finalprice",finalprice);
                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Item added Successfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ProductDescriptionActivity.this,MainActivity.class));
                            finish();
                        }
                        else
                        {
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Failed to add item",Toast.LENGTH_LONG).show();
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
        String finalPrice = Integer.toString(finalprice);
        return finalPrice;
    }
}
