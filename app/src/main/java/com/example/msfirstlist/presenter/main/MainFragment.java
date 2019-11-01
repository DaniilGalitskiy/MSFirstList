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
    private LinearLayout mainToolbarLinear;
    private EditText mainSearchEditText;
    private ProgressBar mainProgressBar;
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

        swipeRefreshLayout = view.findViewById(R.id.mainSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.reloadRepos();
            swipeRefreshLayout.setRefreshing(false);
        });


        final RecyclerView mainRecyclerView = view.findViewById(R.id.mainRecyclerView);
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        mainRecyclerView.setLayoutManager(linearLayout);
        mainRecyclerView.setAdapter(reposAdapter);


        final Toolbar toolbar = view.findViewById(R.id.mainToolbar);

        emptyTextView = view.findViewById(R.id.emptyTextView);

        mainToolbarLinear = view.findViewById(R.id.mainToolbarLinear);

        mainSearchEditText = view.findViewById(R.id.mainClearSearchQuery);

        mainProgressBar = view.findViewById(R.id.mainProgressBar);
        final ImageView mainBackSearchQuery = view.findViewById(R.id.mainBackSearchQuery);
        final ImageView mainClearSearchQuery = view.findViewById(R.id.mainClearImageView);


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
            InputMethodManager imm;
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        mainClearSearchQuery.setOnClickListener(v -> {
            mainSearchEditText.setText("");
            presenter.onClearSearchClick();
        });

        toolbar.setTitle(R.string.app_name);
        getActivity().getMenuInflater().inflate(R.menu.fragment_main_menu, toolbar.getMenu());
        searchMenuItem = toolbar.getMenu().findItem(R.id.actionSearch);
        searchMenuItem.setOnMenuItemClickListener(item -> {
            presenter.onSearchMenuItemClick();
            mainSearchEditText.requestFocus();
            mainSearchEditText.setFocusableInTouchMode(true);
            if (mainSearchEditText.requestFocus()) {
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
            mainToolbarLinear.setVisibility(View.VISIBLE);
//            Animation fadeIn = new AlphaAnimation(0, 1);
//            fadeIn.setDuration(100);
//            AnimationSet animation = new AnimationSet(true);
//            animation.addAnimation(fadeIn);
//            mainToolbarLinear.setAnimation(animation);
            Animation aniFadeIn = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in_toolbar_search);
            mainToolbarLinear.startAnimation(aniFadeIn);
            searchMenuItem.setVisible(false);
        } else {
            mainToolbarLinear.setVisibility(View.GONE);
//            Animation fadeOut = new AlphaAnimation(1, 0);
//            fadeOut.setDuration(100);
//            AnimationSet animation = new AnimationSet(true);
//            animation.addAnimation(fadeOut);
//            mainToolbarLinear.setAnimation(animation);
            Animation aniFadeOut = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out_toolbar_search);
            mainToolbarLinear.startAnimation(aniFadeOut);
            searchMenuItem.setVisible(true);
        }
    }

    @Override
    public void setRepos(List<Repo> repoList) {
        reposAdapter.setReposes(repoList);
    }

    @Override
    public void setSearchQueryText(String query) {
        mainSearchEditText.setText(query);
    }

    @Override
    public void showLoader(boolean visible) {
        if (visible)
            mainProgressBar.setVisibility(View.VISIBLE);
        else
            mainProgressBar.setVisibility(View.GONE);
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
