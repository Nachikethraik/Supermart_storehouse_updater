package com.example.shopkeeper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkeeper.Model.Item;
import com.example.shopkeeper.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<Item> mItem;

    public ItemAdapter(List<Item> mItem) {
        this.mItem = mItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bar, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item item = mItem.get(position);
        holder.itemname.setText(item.getName());
        holder.itemmrp.setText(item.getMRP());
        holder.itemdiscount.setText(item.getDiscount());
        holder.itemquantity.setText(item.getQuantity());
        holder.finalprice.setText(item.getFinalprice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemname;
        private TextView itemmrp;
        private TextView itemquantity;
        private TextView itemdiscount;
        private TextView finalprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname_itembar);
            itemmrp = itemView.findViewById(R.id.itemmrp_itembar);
            itemquantity = itemView.findViewById(R.id.itemquantity_itembar);
            itemdiscount = itemView.findViewById(R.id.itemdiscount_itembar);
            finalprice = itemView.findViewById(R.id.itemfinalprice_itembar);
        }
    }
}
