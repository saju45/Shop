package com.example.sirajstore.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirajstore.Activity.GodownEditActivity;
import com.example.sirajstore.Activity.ItemInAcitivity;
import com.example.sirajstore.Activity.OutActivity;
import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.SimpleGodawnBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


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

        holder.binding.simpleProductName.setText(model.getName());
        holder.binding.simplePoriman.setText(model.getPoriman()+"");
        holder.binding.updateTime.setText(model.getTime());
        holder.binding.simpleType.setText(model.getDhoron());
        holder.binding.simpleGodownKroy.setText(model.getKroymullo()+"");
        holder.binding.simpleGodownMot.setText(model.getMotdam()+"");


        holder.binding.popUpBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu=new PopupMenu(context,v);
                popupMenu.setGravity(Gravity.END);

                popupMenu.getMenu().add("এডিট").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent=new Intent(context,GodownEditActivity.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("type",model.getDhoron());
                        intent.putExtra("kroy",model.getKroymullo());
                        intent.putExtra("poriman",model.getPoriman());
                        intent.putExtra("pushId",model.getPushId());
                        context.startActivity(intent);

                        return true;
                    }
                });

                popupMenu.getMenu().add("প্রবেশ").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent=new Intent(context, ItemInAcitivity.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("poriman",model.getPoriman());
                        intent.putExtra("kroy_mullo",model.getKroymullo());
                        intent.putExtra("type",model.getDhoron());
                        intent.putExtra("pushId",model.getPushId());
                        context.startActivity(intent);
                        return true;
                    }
                });

                popupMenu.getMenu().add("বাহির").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent=new Intent(context, OutActivity.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("poriman",model.getPoriman());
                        intent.putExtra("kroy_mullo",model.getKroymullo());
                        intent.putExtra("type",model.getDhoron());
                        intent.putExtra("pushId",model.getPushId());
                        context.startActivity(intent);
                        return true;
                    }
                });


                popupMenu.getMenu().add("ডিলেট").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setTitle("িলেট করবেন ?");
                        builder.setCancelable(false);

                        builder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FirebaseDatabase.getInstance().getReference()
                                        .child("godawn")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .child(model.getPushId())
                                        .removeValue();
                            }
                        });

                        builder.setNegativeButton("না", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return goadawnModels.size();
    }

    public void filterList(ArrayList<GoadawnModel> filteredList) {
        goadawnModels = filteredList;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleGodawnBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleGodawnBinding.bind(itemView);
        }
    }
}
