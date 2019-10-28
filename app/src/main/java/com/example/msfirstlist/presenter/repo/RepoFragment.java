package com.example.msfirstlist.presenter.repo;

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

public class RepoFragment extends MvpFragment implements RepoView{

    private static final String EXTRA_REPO = "extra_repo";

    private Button backButton;
    private TextView repoTextView;

    @Inject
    Router router;

    @InjectPresenter
    RepoPresenter presenter;

    @ProvidePresenter
    public RepoPresenter createReposPresenter(){
        return new RepoPresenter(router, getExtraRepos());
    }

    public RepoFragment getNewInstance(String repos){
        RepoFragment repoFragment = new RepoFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_REPO, repos);
        repoFragment.setArguments(args);
        return repoFragment;
    }

    public String getExtraRepos(){
        return getArguments().getString(EXTRA_REPO);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
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
        backButton = view.findViewById(R.id.back_button);
        repoTextView = view.findViewById(R.id.repo_textView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repoTextView.setText(getExtraRepos());
        backButton.setOnClickListener(v -> presenter.onBackPressed());

    }


}
