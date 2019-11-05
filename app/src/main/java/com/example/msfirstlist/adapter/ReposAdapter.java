package com.example.msfirstlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.msfirstlist.R;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.Collections;
import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private List<Repo> reposes = Collections.emptyList();

    private Listener listener;


    public ReposAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setReposes(List<Repo> reposes) {
        this.reposes = reposes;
        notifyDataSetChanged();
    }

    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        return new ReposAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder holder, int position) {
        holder.tvMainList.setText(reposes.get(position).getName());
    }

//    @Override
//    public long getItemId(int position) {
//        return reposes.get(position).getId();
//    }
//
//
//    public String getItemName(int position) {
//        return reposes.get(position).getName();
//    }

    @Override
    public int getItemCount() {
        return reposes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMainList;

        ViewHolder(View itemView) {
            super(itemView);
            tvMainList = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener(v -> {
                listener.onItemClick(reposes.get(getAdapterPosition()));
            });
        }
    }

    public interface Listener {
        void onItemClick(Repo repo);
    }
}