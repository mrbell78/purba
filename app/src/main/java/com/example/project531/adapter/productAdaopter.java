package com.example.project531.adapter;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project531.Activity.CartActivity;
import com.example.project531.Activity.ShowDetailActivity;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.R;
import com.example.project531.responsedata.responseProduct;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class productAdaopter extends RecyclerView.Adapter<productAdaopter.customClass> {
    String searchadapter;
    Object activity;
    List<responseProduct> data;
   List<responseProduct> data_filter;
    Context context;
    private ManagementCart managementCart;
    public productAdaopter(List<responseProduct> data, Context context) {
        this.data = data;
        this.context = context;
        managementCart = new ManagementCart(context);
        this.data_filter = data;
    }
    @NonNull
    @Override
    public productAdaopter.customClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_recommended,parent,false);
        return  new productAdaopter.customClass(view);
    }
    @Override
    public void onBindViewHolder(@NonNull customClass holder, int position) {
        if(data.get(position).getPro_name()!=null){
            holder.textView8.setText(""+data.get(position).getPro_name());
        }else{
            holder.textView8.setText("Null");
        }
        holder.price.setText("Price  : "+data.get(position).getSalePrice());
        if(data.get(position).getUnit()!=null){
            holder.unit.setText("Unit: "+data.get(position).getUnit().toString());

        }else{
            holder.unit.setText("Null");
        }
        Picasso.with(context)
                .load(data.get(position).getImageFile().toString())
                .into(holder.imageView3);
        holder.textView8.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
            intent.putExtra("object", (Serializable) data.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
         int[] numberOrder = {1};
        holder.numberItemTxt.setText(String.valueOf(numberOrder[0]));
        holder.plusCardBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder[0] = numberOrder[0] + 1;
                holder.numberItemTxt.setText(String.valueOf(numberOrder[0]));
            }
        });
        holder.minusCardBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder[0] > 1) {
                    numberOrder[0] = numberOrder[0] - 1;
                }
                holder.numberItemTxt.setText(String.valueOf(numberOrder[0]));
            }
        });
        holder.add_to_cart.setOnClickListener(v -> {
            data.get(position).setNumberInCart(numberOrder[0]);
            Intent intent = new Intent(holder.itemView.getContext(), CartActivity.class);
            managementCart.insertFood((responseProduct)data.get(position));
           // holder.itemView.getContext().startActivity(intent);



        });
    }
    @Override
    public int getItemCount() {
        return data_filter.size();
    }
    public static final class customClass extends RecyclerView.ViewHolder {
        TextView textView8,price,unit, numberItemTxt, add_to_cart, count_item;
        ImageView imageView3, plusCardBtn2, minusCardBtn2;
        ConstraintLayout mainLayout;
        public customClass(@NonNull View itemView) {
            super(itemView);
            textView8 =itemView.findViewById(R.id.textView8);
            price =itemView.findViewById(R.id.price);
            unit =itemView.findViewById(R.id.unit);
            imageView3 =itemView.findViewById(R.id.imageView3);
            numberItemTxt =itemView.findViewById(R.id.numberItemTxt2);
            plusCardBtn2 =itemView.findViewById(R.id.plusCardBtn2);
            minusCardBtn2 =itemView.findViewById(R.id.minusCardBtn2);
            add_to_cart =itemView.findViewById(R.id.add_to_cart);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

    public Filter getFilterdata(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if(key.isEmpty()){
                    data_filter = data;
                }else{
                    List<responseProduct>filter = new ArrayList<>();
                    for (responseProduct row:data_filter){
                        if(row.getPro_name().toLowerCase().contains(key.toLowerCase())){
                            filter.add(row);
                        }
                    }
                    data_filter = filter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data_filter;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data_filter = (List<responseProduct>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
