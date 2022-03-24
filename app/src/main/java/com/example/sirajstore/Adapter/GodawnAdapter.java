package com.example.sirajstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.SimpleGodawnBinding;

import java.util.ArrayList;

public class GodawnAdapter extends RecyclerView.Adapter<GodawnAdapter.viewHolder> {

    Context context;
    ArrayList<GoadawnModel> goadawnModels;

    public GodawnAdapter(Context context, ArrayList<GoadawnModel> goadawnModels) {
        this.context = context;
        this.goadawnModels = goadawnModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_godawn,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        GoadawnModel model=goadawnModels.get(position);

    }

    @Override
    public int getItemCount() {
        return goadawnModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleGodawnBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleGodawnBinding.bind(itemView);
        }
    }
}
