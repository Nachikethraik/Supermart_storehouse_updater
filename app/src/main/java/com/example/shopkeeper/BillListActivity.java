package com.example.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopkeeper.Adapter.BillAdapter;
import com.example.shopkeeper.Adapter.ItemAdapter;
import com.example.shopkeeper.Model.Bill;
import com.example.shopkeeper.Model.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private List<Bill> mBill;
    EditText search_bill;
    TextView total, noofbills;
    public int TOTAL=0, NOOFBILLS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bill List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_bill);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mBill = new ArrayList<>();
        readBills();
        search_bill = findViewById(R.id.search_bills);
        total = findViewById(R.id.total);
        noofbills = findViewById(R.id.noofbills);
        search_bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchBills(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void searchBills(final String s)
    {
        Query query = FirebaseDatabase.getInstance().getReference("Bills").orderByChild("BillNumber").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBill.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Bill bill = snapshot.getValue(Bill.class);
                    assert bill!=null;
                    mBill.add(bill);
                }
                billAdapter = new BillAdapter(mBill);
                recyclerView.setAdapter(billAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readBills()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bills");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBill.clear();
                if(search_bill.getText().toString().equals(""))
                {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Bill bill= snapshot.getValue(Bill.class);
                        assert bill!=null;
                        mBill.add(bill);
                        TOTAL = TOTAL + Integer.parseInt(bill.getTotalAmount());
                        NOOFBILLS++;
                    }
                    total.setText(Integer.toString(TOTAL));
                    noofbills.setText(Integer.toString(NOOFBILLS));
                    billAdapter = new BillAdapter(mBill);
                    recyclerView.setAdapter(billAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
