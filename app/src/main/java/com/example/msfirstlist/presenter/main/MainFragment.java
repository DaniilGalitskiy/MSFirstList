package com.example.msfirstlist.presenter.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.msfirstlist.R;
import com.example.msfirstlist.adapter.Entity.ReposList;
import com.example.msfirstlist.adapter.ReposAdapter;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

public class MainFragment extends MvpFragment implements MainView, ReposAdapter.ItemClickListener {

    private ReposAdapter reposAdapter;
    private ReposList reposList;

    private TextView emptyTextView;
    private MenuItem searchMenuItem;
    private LinearLayout mainToolbarLinear;
    private EditText mainSearchEditText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mainRecyclerView;

    @InjectPresenter
    MainPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    public static MainFragment getNewInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }


    public void init(View view){

        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);

        Toolbar toolbar = view.findViewById(R.id.mainToolbar);

        emptyTextView = view.findViewById(R.id.emptyTextView);
        mainToolbarLinear = view.findViewById(R.id.mainToolbarLinear);
        mainSearchEditText = view.findViewById(R.id.mainClearSearchQuery);
        swipeRefreshLayout = view.findViewById(R.id.mainSwipeRefresh);
        final ImageView mainBackSearchQuery = view.findViewById(R.id.mainBackSearchQuery);
        final ImageView mainClearSearchQuery = view.findViewById(R.id.mainClearImageView);

        reposList = new ReposList();
        reposAdapter = new ReposAdapter();
//        presenter.reloadRepos();

//        reposAdapter = new ReposAdapter(reposList.getReposes());
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(linearLayout);

        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
            reposAdapter.notifyDataSetChanged();
        }, 1000));

        mainSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onQueryTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mainBackSearchQuery.setOnClickListener(v -> {
            presenter.onCloseSearchClick();
            mainToolbarLinear.setVisibility(View.GONE);
            searchMenuItem.setVisible(true);
            InputMethodManager imm;
            imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });

        mainClearSearchQuery.setOnClickListener(v -> presenter.onQueryTextChanged(""));


        toolbar.setTitle(R.string.app_name);
        getActivity().getMenuInflater().inflate(R.menu.fragment_main_menu, toolbar.getMenu());
        searchMenuItem = toolbar.getMenu().findItem(R.id.actionSearch);
        searchMenuItem.setOnMenuItemClickListener(item -> {
            presenter.onSearchMenuItemClick();
            mainSearchEditText.requestFocus();
            mainSearchEditText.setFocusableInTouchMode(true);
            if(mainSearchEditText.requestFocus()) {
                InputMethodManager imm;
                imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
            return false;
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void setSearchActionViewVisible(boolean isVisible) {
        if (isVisible){
            mainToolbarLinear.setVisibility(View.VISIBLE);
            searchMenuItem.setVisible(false);
        }
        else{
            mainToolbarLinear.setVisibility(View.GONE);
            searchMenuItem.setVisible(true);
        }
    }

    @Override
    public void showLoader() {

    }

    @Override
    public void setAdapter(List<Repo> repoList) {
        reposAdapter.setReposes(repoList);
        reposAdapter.notifyDataSetChanged();
        reposAdapter.setClickListener(this);
        mainRecyclerView.setAdapter(reposAdapter);
    }

    @Override
    public void setSearchQueryText(String query) {
        reposList.filter(query);
        reposAdapter.notifyDataSetChanged();

        if (reposList.getReposes().size() == 0)
        {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }

        if (mainSearchEditText.getText().toString().equals(query)) return;
        mainSearchEditText.setText(query);
    }

    @SuppressLint("ShowToast")
    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.linearMainList:
                presenter.onItemClicked(reposAdapter.getItemName(position));
                break;
            default:
                break;
        }
    }
}
