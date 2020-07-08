package com.example.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shopkeeper.Adapter.ItemAdapter;
import com.example.shopkeeper.Model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> mItem;
    EditText search_item;
    TextView noofitems;
    public int NOOFITEMS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Item List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_items);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mItem = new ArrayList<>();
        readItems();
        search_item = findViewById(R.id.search_items);
        noofitems = findViewById(R.id.noofitems);
        search_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchItems(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void searchItems(final String s)
    {
        Query query = FirebaseDatabase.getInstance().getReference("Items").orderByChild("search").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mItem.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Item item = snapshot.getValue(Item.class);
                    assert item!=null;
                    mItem.add(item);
                }
                itemAdapter = new ItemAdapter(mItem);
                recyclerView.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readItems()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mItem.clear();
                if(search_item.getText().toString().equals(""))
                {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Item item = snapshot.getValue(Item.class);
                        assert item!=null;
                        mItem.add(item);
                        NOOFITEMS++;

                    }
                    noofitems.setText(Integer.toString(NOOFITEMS));
                    itemAdapter = new ItemAdapter(mItem);
                    recyclerView.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
