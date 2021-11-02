package com.example.project531.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.Interface.ChangeNumberItemsListener;
import com.example.project531.R;
import com.example.project531.responsedata.responseProduct;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<responseProduct> listFoodSelected;
    ArrayList list = new ArrayList();
    private OnItemNumber datasize;
    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;
    Context context;
    public CartListAdapter(ArrayList<responseProduct> listFoodSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listFoodSelected = listFoodSelected;
        managementCart = new ManagementCart(context);
        this.context=context;
        this.changeNumberItemsListener = changeNumberItemsListener;

        try {

            this.datasize = ((OnItemNumber)context);

        }catch (ClassCastException e){
            throw  new ClassCastException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listFoodSelected.get(position).getPro_name());
        holder.feeEachItem.setText("Tk " + listFoodSelected.get(position).getSalePrice());
        holder.totalEachItem.setText("TK " + Math.round((listFoodSelected.get(position).getNumberInCart() * listFoodSelected.get(position).getSalePrice())));
        holder.num.setText(String.valueOf(listFoodSelected.get(position).getNumberInCart()));

        Picasso.with(holder.itemView.getContext())
                .load(listFoodSelected.get(position).getImageFile().toString())
                .into(holder.pic);

/*
        int drawableReourceId = holder.itemView.getContext().getResources()
                .getIdentifier(listFoodSelected.get(position).getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.pic);*/

        holder.plusItem.setOnClickListener(v -> managementCart.plusNumberFood(listFoodSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();



            list.add(listFoodSelected.get(position).getNumberInCart());
            Intent intent = new Intent();
            intent.putExtra("quantity",list.size());
           datasize.dataSize(intent);
        }));

        holder.minusItem.setOnClickListener(v -> managementCart.minusNumberFood(listFoodSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));
    }

    @Override
    public int getItemCount() {
        return listFoodSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);
            num = itemView.findViewById(R.id.numberItemTxt);

        }
    }


    public interface OnItemNumber {

        public void dataSize(Intent intent);
    }
}
