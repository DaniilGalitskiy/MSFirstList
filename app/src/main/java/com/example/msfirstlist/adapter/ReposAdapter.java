package com.example.msfirstlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.msfirstlist.R;
import com.example.msfirstlist.adapter.Entity.Repos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private Context context;
    private List<Repos> reposList;
    private ArrayList<Repos> reposArrayList;

    private View view;
    private View.OnClickListener onClickListener;
    LayoutInflater lInflater;


    public ReposAdapter(List<Repos> repos, Context context, View.OnClickListener onClickListener) {
        this.reposList = repos;
        this.context = context;
        this.onClickListener = onClickListener;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.reposArrayList = new ArrayList<Repos>();
        this.reposArrayList.addAll(reposList);
    }


    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_main, parent, false);

        return new ReposAdapter.ViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder holder, int position) {
        holder.tvMainList.setText(reposList.get(position).getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        reposList.clear();
        if (charText.length()==0){
            reposList.addAll(reposArrayList);
        }
        else {
            for (Repos r : reposArrayList){
                if (r.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    reposList.add(r);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMainList;

        ViewHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            tvMainList = view.findViewById(R.id.tvMainList);
            view.setOnClickListener(onClickListener);
        }
    }
}



    /*@Override
    public int getCount() {
        return reposList.size();
    }

    @Override
    public Object getItem(int position) {
        return reposList.get(position);
    }*/
    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.list_item_main, parent, false);
        }
        Repos repos = reposList.get(position);
        ((TextView) view.findViewById(R.id.tvMainList)).setText(repos.getName());
        view.findViewById(R.id.linearMainList).setOnClickListener(onClickListener);
        return view;
    }*/