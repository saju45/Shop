package com.example.sirajstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.SimpleInLayoutBinding;

import java.util.ArrayList;

public class ItemInAdapter extends RecyclerView.Adapter<ItemInAdapter.viewHolder>{


    Context context;
    ArrayList<GoadawnModel> list;

    public ItemInAdapter(Context context, ArrayList<GoadawnModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.simple_in_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        GoadawnModel model=list.get(position);

        holder.binding.name.setText(model.getName());
        holder.binding.poriman.setText(model.getPoriman()+"");
        holder.binding.tye.setText(model.getDhoron());
        holder.binding.time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
         SimpleInLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SimpleInLayoutBinding.bind(itemView);

        }
    }
}
