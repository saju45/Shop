package com.example.sirajstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sirajstore.Adapter.ItemInAdapter;
import com.example.sirajstore.Adapter.ItemOutAdapter;
import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.databinding.FragmentItemOutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ItemOutFragment extends Fragment {


    FragmentItemOutBinding binding;
    DatabaseReference databaseReference;
    ArrayList<GoadawnModel> list;

    public ItemOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentItemOutBinding.inflate(inflater, container, false);

        list=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Out");
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.outRecyclerview.setLayoutManager(layoutManager);

        ItemOutAdapter adapter=new ItemOutAdapter(getContext(),list);
        binding.outRecyclerview.setAdapter(adapter);

        databaseReference.child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            GoadawnModel model=snapshot1.getValue(GoadawnModel.class);
                            list.add(model);
                        }adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return binding.getRoot();
    }
}