package com.arbaelbarca.githubsearchuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbaelbarca.githubsearchuser.R;
import com.arbaelbarca.githubsearchuser.di.qulifier.ApplicationContext;
import com.arbaelbarca.githubsearchuser.model.ItemsItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

public class AdapterListUserNew extends RecyclerView.Adapter<AdapterListUserNew.ViewHolder> {
    private ArrayList<ItemsItem> itemArrayList;

    private AdapterListUserNew.ClickListener clickListener;

    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    public AdapterListUserNew(AdapterListUserNew.ClickListener clickListener) {
        this.clickListener = clickListener;
        itemArrayList = new ArrayList<>();
    }

    public interface ClickListener {
        void launchIntent(int pos);
    }


    public void setData(ArrayList<ItemsItem> data) {
        this.itemArrayList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_listuser, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsItem itemsItem = itemArrayList.get(position);
        holder.txtName.setText(itemsItem.getLogin());

        Glide.with(mContext)
                .load(itemsItem.getAvatarUrl())
                .into(holder.imgUser);

        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null)
                clickListener.launchIntent(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtUser);
            imgUser = itemView.findViewById(R.id.imgUser);


        }
    }
}
