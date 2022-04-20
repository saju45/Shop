package com.example.sirajstore.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirajstore.Activity.EditActivity;
import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.Model.ProductModel;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.SimpleLayoutBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {

    Context context;
    ArrayList<ProductModel> list;
    String pushKey;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ProductModel model=list.get(position);

        holder.binding.simpleProductName.setText(model.getName());
        holder.binding.simplePoriman.setText(model.getPoriman());
        holder.binding.simpleGodownKroy.setText(model.getBuy());
        holder.binding.simpleSell.setText(model.getSell());
        holder.binding.updateTime.setText(model.getTime());

        holder.binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,EditActivity.class);
                intent.putExtra("productName",model.getName());
                intent.putExtra("poriman",model.getPoriman());
                intent.putExtra("buy",model.getBuy());
                intent.putExtra("sell",model.getSell());
                intent.putExtra("key",model.getPushId());
                context.startActivity(intent);
            }
        });

        holder.binding.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("ডিলেট করবেন ?");
                builder.setCancelable(false);

                builder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Product").child(model.getPushId());

                      reference.removeValue();

                    }
                });
                builder.setNegativeButton("না", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SimpleLayoutBinding.bind(itemView);
        }
    }

    public void filterList(ArrayList<ProductModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
