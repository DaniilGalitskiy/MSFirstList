package com.example.msfirstlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.msfirstlist.R;
import com.example.msfirstlist.adapter.Entity.Repos;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private List<Repos> reposes;

    private View view;
    private ItemClickListener onClickListener;


    public ReposAdapter(List<Repos> reposList) {
        this.reposes = reposList;
    }

    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        return new ReposAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder holder, int position) {
        holder.tvMainList.setText(reposes.get(position).getName());
    }

    @Override
    public long getItemId(int position) {
        return reposes.get(position).getId();
    }


    public String getItemName(int position) {
        return reposes.get(position).getName();
    }

    @Override
    public int getItemCount() {
        return reposes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMainList;

        ViewHolder(View itemView) {
            super(itemView);
            tvMainList = view.findViewById(R.id.main_item_textView);
            itemView.setOnClickListener(v -> {
                if (onClickListener != null)
                    onClickListener.onItemClick(v, getAdapterPosition());
            });
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}