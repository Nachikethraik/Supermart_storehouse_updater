package com.example.shopkeeper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkeeper.Model.Bill;
import com.example.shopkeeper.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

        List<Bill> mBill;

public BillAdapter(List<Bill> mBill) {
        this.mBill= mBill;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_bar, parent, false);
        return new BillAdapter.ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
final Bill bill = mBill.get(position);
    holder.billnumber.setText(bill.getBillNumber());
    holder.customername.setText(bill.getCustomerName());
    holder.noofitems.setText(bill.getNoofItems());
    holder.totalamount.setText(bill.getTotalAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        }
        });
        }

@Override
public int getItemCount() {
        return mBill.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView billnumber;
    private TextView noofitems;
    private TextView totalamount;
    private TextView customername;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        billnumber = itemView.findViewById(R.id.billnumber_billbar);
        customername = itemView.findViewById(R.id.customername_billbar);
        noofitems = itemView.findViewById(R.id.noofitem_billbar);
        totalamount = itemView.findViewById(R.id.totalamount_billbar);
    }
}
}
