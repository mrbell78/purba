package com.example.project531.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Helper.ManagementCart;
import com.example.project531.Interface.ChangeNumberItemsListener;
import com.example.project531.R;
import com.example.project531.responsedata.responseProduct;

import java.util.ArrayList;

public class checkoutAdaptor extends RecyclerView.Adapter<checkoutAdaptor.ViewHolder> {
    ArrayList<responseProduct> listFoodSelected;
    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;
    public checkoutAdaptor(ArrayList<responseProduct> listFoodSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listFoodSelected = listFoodSelected;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public checkoutAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_checkout_item, parent, false);
        return new checkoutAdaptor.ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull checkoutAdaptor.ViewHolder holder, int position) {
        holder.item.setText(listFoodSelected.get(position).getPro_name());
        holder.feeEachItem.setText("" +listFoodSelected.get(position).getSalePrice());
        holder.totalEachItem.setText("" + Math.round((listFoodSelected.get(position).getNumberInCart() * listFoodSelected.get(position).getSalePrice())));
        holder.num.setText(String.valueOf(listFoodSelected.get(position).getNumberInCart()));

    }

    @Override
    public int getItemCount() {
        return listFoodSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            totalEachItem = itemView.findViewById(R.id.total);
            feeEachItem = itemView.findViewById(R.id.totalEachItem2);
            num = itemView.findViewById(R.id.feeEachItem);
        }
    }
}
