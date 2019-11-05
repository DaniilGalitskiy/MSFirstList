package com.example.msfirstlist.presenter.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.msfirstlist.adapter.ReposAdapter;
import com.example.msfirstlist.repository.net.entity.Repo;

import java.util.List;

public class MainFragment extends MvpFragment implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    private ReposAdapter reposAdapter = new ReposAdapter((Repo repo) -> presenter.onItemClicked(repo));

    private TextView emptyTextView;
    private MenuItem searchMenuItem;
    private LinearLayout toolbarLinear;
    private EditText searchEditText;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static MainFragment getNewInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
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


    public void init(View view) {

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.reloadRepos();
            swipeRefreshLayout.setRefreshing(false);
        });


        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(reposAdapter);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        emptyTextView = view.findViewById(R.id.emptyTextView);
        toolbarLinear = view.findViewById(R.id.toolbarLinear);
        searchEditText = view.findViewById(R.id.clearSearchQuery);

        progressBar = view.findViewById(R.id.progressBar);
        final ImageView backSearchQuery = view.findViewById(R.id.backSearchQuery);
        final ImageView clearSearchQuery = view.findViewById(R.id.clearImageView);


        searchEditText.addTextChangedListener(new TextWatcher() {
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

        backSearchQuery.setOnClickListener(v -> {
            presenter.onCloseSearchClick();
            InputMethodManager imm;
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        clearSearchQuery.setOnClickListener(v -> {
            presenter.onClearSearchClick();
        });

        toolbar.setTitle(R.string.app_name);
        getActivity().getMenuInflater().inflate(R.menu.fragment_main_menu, toolbar.getMenu());
        searchMenuItem = toolbar.getMenu().findItem(R.id.actionSearch);
        searchMenuItem.setOnMenuItemClickListener(item -> {
            presenter.onSearchMenuItemClick();
            searchEditText.requestFocus();
            searchEditText.setFocusableInTouchMode(true);
            if (searchEditText.requestFocus()) {
                InputMethodManager imm;
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        if (isVisible) {
            toolbarLinear.setVisibility(View.VISIBLE);
            Animation aniFadeIn = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in_toolbar_search);
            toolbarLinear.startAnimation(aniFadeIn);
            searchMenuItem.setVisible(false);
        } else {
            toolbarLinear.setVisibility(View.GONE);
            Animation aniFadeOut = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out_toolbar_search);
            toolbarLinear.startAnimation(aniFadeOut);
            searchMenuItem.setVisible(true);
        }
    }

    @Override
    public void setRepos(List<Repo> repoList) {
        reposAdapter.setReposes(repoList);
    }

    @Override
    public void setSearchQueryText(String query) {
        searchEditText.setText(query);
        searchEditText.setSelection(searchEditText.getText().length());
    }

    @Override
    public void showLoader(boolean visible) {
        if (visible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setVisibleEmptySearchRepos() {
        if (reposAdapter.getItemCount() == 0)
            emptyTextView.setVisibility(View.VISIBLE);
        else
            emptyTextView.setVisibility(View.GONE);
    }

    @SuppressLint("ShowToast")
    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
