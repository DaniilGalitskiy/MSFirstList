package com.example.msfirstlist.presenter.repos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.msfirstlist.App;
import com.example.msfirstlist.R;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class ReposFragment extends MvpFragment implements ReposView{

    private static final String EXTRA_REPOS = "extra_repos";

    private Button btBack;
    private TextView tvRepos;

    @Inject
    Router router;

    @InjectPresenter
    ReposPresenter presenter;

    @ProvidePresenter
    public ReposPresenter createReposPresenter(){
        return new ReposPresenter(router, "test");
    }

    public ReposFragment getNewInstance(String repos){
        ReposFragment reposFragment = new ReposFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_REPOS, repos);
        reposFragment.setArguments(args);
        return reposFragment;
    }

    public String getExtraRepos(){
        return getArguments().getString(EXTRA_REPOS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btBack = view.findViewById(R.id.btBack);
        tvRepos = view.findViewById(R.id.tvRepos);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvRepos.setText(getExtraRepos());
        btBack.setOnClickListener(v -> presenter.onBackPressed());

    }


}
