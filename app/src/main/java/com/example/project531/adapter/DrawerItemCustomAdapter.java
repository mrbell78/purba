
package com.example.project531.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Activity.MainActivity;
import com.example.project531.Activity.ShowDetailActivity;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.R;
import com.example.project531.categorySerachActivity;
import com.example.project531.responsedata.responseCategory;
import com.example.project531.responsedata.responseProduct;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class DrawerItemCustomAdapter extends RecyclerView.Adapter<DrawerItemCustomAdapter.customClass> {
    List<responseCategory> data;
    Context context;
    public DrawerItemCustomAdapter(List<responseCategory> data, Context context) {
        this.data = data;
        this.context = context;

    }
    @NonNull
    @Override
    public DrawerItemCustomAdapter.customClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item_row,parent,false);
        return  new DrawerItemCustomAdapter.customClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerItemCustomAdapter.customClass holder, int position) {

        if(data.get(position).getInvenType()!=null){
            holder.textViewName.setText(""+data.get(position).getInvenType().toString());
        }else{
            holder.textViewName.setText("Null");
        }
        Picasso.with(context)
                .load(data.get(position).getImg_path().toString())
                .into(holder.imageView3);
        holder.textViewName.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), categorySerachActivity.class);
           intent.putExtra("getInvenCode", data.get(position).getInvenCode());
            holder.itemView.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class customClass extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageView3;
        public customClass(@NonNull View itemView) {
            super(itemView);
            textViewName =itemView.findViewById(R.id.category);
            imageView3 =itemView.findViewById(R.id.imageViewIcon);

        }
    }
}
