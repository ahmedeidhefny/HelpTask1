package com.hardtask.eid.ahmed.souqtask.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardtask.eid.ahmed.souqtask.AppConfig;
import com.hardtask.eid.ahmed.souqtask.Category;
import com.hardtask.eid.ahmed.souqtask.CategoryAdapter;
import com.hardtask.eid.ahmed.souqtask.CategoryViewModel;
import com.hardtask.eid.ahmed.souqtask.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SubCategoryFragment extends Fragment {

    @BindView(R.id.massage_loading)
    TextView massageLoading;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.progress_layout)
    LinearLayout progressLayout;
    private Category category;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.subcategory_recycler)
    RecyclerView subcategoryRecycler;

    private SharedPreferences sharedPreferences;
    private String language;
    private CategoryViewModel viewModel;

    private int categoryId = 0;
    private int countryId = 1;

    @OnClick(R.id.icon_back)
    public void onViewClicked() {
        //back to previous fragment
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getArguments from Adapter
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable(AppConfig.INTENT_CategoryKey);
            categoryId = category.getId();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View myView = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, myView);
        //show progressBar and set progressLayout VISIBLE
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.show();
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        subcategoryRecycler.setHasFixedSize(true);
        subcategoryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //get language from sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = sharedPreferences.getString(AppConfig.ShardPreference_Language_Key, "en");
        //check languageType to handle the category Title
        if (language.equals("ar")) {
            toolbarTitle.setText(category.getTitleAR());
        } else {
            toolbarTitle.setText(category.getTitleEN());
        }
        //fetch subCategory from api and viewModel
        viewModel.getCategories(String.valueOf(categoryId), String.valueOf(countryId)).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                //hide progressBar and set progressLayout INVISIBLE
                progressLayout.setVisibility(View.INVISIBLE);
                progressBar.hide();
                if (categories.size() <= 0) {
                    progressLayout.setVisibility(View.VISIBLE);
                    massageLoading.setText(getString(R.string.not_found_category_labal));
                }
                //set Adapter to category RecyclerView
                CategoryAdapter adapter = new CategoryAdapter(getActivity(), categories);
                subcategoryRecycler.setAdapter(adapter);
            }
        });
        return myView;
    }

}
