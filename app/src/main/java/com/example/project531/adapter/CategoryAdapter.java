package com.example.project531.adapter;


import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project531.Activity.CartActivity;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Activity.ShowDetailActivity;
import com.example.project531.Activity.subCategory;
import com.example.project531.Domain.CategoryDomain;
import com.example.project531.Domain.SubCategoryDomain;
import com.example.project531.R;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<CategoryDomain> categoryDomains;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        String picUrl="";
        switch (position) {
            case 0: {
                picUrl = "ghee";
                break;
            }
            case 1: {
                picUrl = "cheese";
                break;
            }
            case 2: {
                picUrl = "curd";
                break;
            }
            case 3: {
                picUrl = "milk";
                break;
            }
            case 4: {
                picUrl = "sweets";
                break;
            }
        }
        int drawableReourceId = holder.itemView.getContext().getResources()
                .getIdentifier(picUrl, "drawable",
                        holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.categoryPic);


        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), subCategory.class);
            intent.putExtra("object", categoryDomains.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    @Override
    public void onClick(View view) {


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);


        }
    }
}
