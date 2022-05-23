package com.example.appdevelopment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ShopAdapter extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<ShopItem> shopList;
    Context context;
    RecyclerView fullShopList;
    TextView shopItem;

    public ShopAdapter(Context context, ArrayList<ShopItem> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_display, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ShopItem shopItem = shopList.get(position);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

}

