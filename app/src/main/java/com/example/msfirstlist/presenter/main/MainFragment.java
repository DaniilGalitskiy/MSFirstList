package com.example.msfirstlist.presenter.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.msfirstlist.App;
import com.example.msfirstlist.R;
import com.example.msfirstlist.adapter.Entity.ReposList;
import com.example.msfirstlist.adapter.ReposAdapter;

public class MainFragment extends MvpFragment implements MainView {

    private ReposAdapter reposAdapter;

    private Toolbar toolbar;
    private RecyclerView rvMain;
    private LinearLayout linearMainList;
    private TextView tvEmpty;
    private MenuItem searchMenuItem;

    @InjectPresenter
    MainPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.INSTANCE.getAppComponent().inject(this);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    public static MainFragment getNewInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initComponent(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initComponent(View view) {
        rvMain = view.findViewById(R.id.rvMain);
        linearMainList = view.findViewById(R.id.linearMainList);
        toolbar = view.findViewById(R.id.toolbar);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        presenter.getRepos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main_menu, menu);

        searchMenuItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setMaxWidth(4000);

        if (expanded){
            searchMenuItem.expandActionView();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                reposAdapter.filter(newText);
                if (reposAdapter.getItemCount() == 0) {
                    rvMain.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    rvMain.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState, searchMenuItem.isActionViewExpanded());
    }



    @Override
    public void setAdapter() {
        ReposList reposList = new ReposList();
        reposAdapter = new ReposAdapter(reposList.getReposes(), getActivity(), onClickListener);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMain.setLayoutManager(linearLayout);
        rvMain.setAdapter(reposAdapter);
    }

    @Override
    public void setSearchActionViewExpaned(boolean isExpanded) {
        expanded = isExpanded;
    }

    @Override
    public void showError(String msg) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Error")
                .setCancelable(true)
                .setMessage(msg)
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.cancel();
                    dialog.dismiss();
                });
        AlertDialog alertDialog = adb.create();
        alertDialog.show();
    }

    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.linearMainList:
                presenter.onCardClicked("test");
                break;
            default:
                break;
        }
    };
}
