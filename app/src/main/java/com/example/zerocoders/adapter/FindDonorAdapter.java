package com.example.zerocoders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerocoders.models.Donor;
import com.example.zerocoders.models.Users;
import com.example.zerocoders.R;

import java.util.ArrayList;
import java.util.Calendar;

public class FindDonorAdapter extends  RecyclerView.Adapter<FindDonorAdapter.ViewHolder>{
    static Calendar calendar = Calendar.getInstance();
    ArrayList<Donor> list;
    Context context;

    public FindDonorAdapter(ArrayList<Donor> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FindDonorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview ,parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindDonorAdapter.ViewHolder holder, int position) {
        Donor donor =list.get(position);
        int len = donor.getDob().length();
        String age = "" + (calendar.get(Calendar.YEAR) - Integer.parseInt(donor.getDob().substring(len - 4)));
        holder.name.setText(donor.getName());
        holder.age.setText(age);
        holder.email.setText(donor.getEmail());
        holder.phoneNo.setText(donor.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,age, email, phoneNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.uname);
            age = itemView.findViewById(R.id.UAGE);
            email = itemView.findViewById(R.id.uemail);
            phoneNo = itemView.findViewById(R.id.uMobileNo);
        }
    }
}
