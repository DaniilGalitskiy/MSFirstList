package com.example.msfirstlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.msfirstlist.R;
import com.example.msfirstlist.adapter.Entity.Repo;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private List<Repo> reposes;

    private View view;
    private View.OnClickListener onClickListener;


    public ReposAdapter(List<Repo> reposList, View.OnClickListener onClickListener) {
        this.reposes = reposList;
        this.onClickListener = onClickListener;
    }

    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        return new ReposAdapter.ViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder holder, int position) {
        holder.tvMainList.setText(reposes.get(position).getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return reposes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMainList;

        ViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            tvMainList = view.findViewById(R.id.main_item_textView);
            view.setOnClickListener(onClickListener);
        }
    }
}