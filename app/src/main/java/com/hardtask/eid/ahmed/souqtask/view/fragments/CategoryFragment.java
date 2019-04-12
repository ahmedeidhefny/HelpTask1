package com.hardtask.eid.ahmed.souqtask.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class CategoryFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_change_language)
    ImageView iconChangeLanguage;
    @BindView(R.id.recyclerview)
    RecyclerView catRecyclerView;
    @BindView(R.id.massage_loading)
    TextView massageLoading;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.progress_layout)
    LinearLayout progressLayout;

    private static final int MENU_EN = 1;
    private static final int MENU_AR = 2;

    private int categoryId = 0;
    private int countryId = 1;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CategoryViewModel viewModel;


    @OnClick(R.id.icon_change_language)
    public void onViewClicked() {
        //handle language to register with ContextMenu
        registerForContextMenu(iconChangeLanguage);
        getActivity().openContextMenu(iconChangeLanguage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);

        //show progressBar and set progressLayout VISIBLE
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.show();
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        catRecyclerView.setHasFixedSize(true);
        catRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        //fetch category from api and viewModel
        viewModel.getCategories(String.valueOf(categoryId), String.valueOf(countryId)).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                //hide progressBar and set progressLayout INVISIBLE
                progressLayout.setVisibility(View.INVISIBLE);
                progressBar.hide();
                //set Adapter to category RecyclerView
                CategoryAdapter adapter = new CategoryAdapter(getActivity(), categories);
                catRecyclerView.setAdapter(adapter);
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //set Data to ContextMenu
        menu.setHeaderIcon(R.drawable.ic_language);
        menu.setHeaderTitle("Select Language");
        menu.add(Menu.NONE, MENU_EN, Menu.NONE,getString(R.string.english));
        menu.add(Menu.NONE, MENU_AR, Menu.NONE, getString(R.string.arabic));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // when to click MenuItem of ContextMenu
        switch (item.getItemId()) {
            case MENU_EN:
                // save language to shardPreferences
                editor.putString(AppConfig.ShardPreference_Language_Key, "en");
                editor.commit();
                //recreate the acivity
                getActivity().recreate();
                break;
            case MENU_AR:
                editor.putString(AppConfig.ShardPreference_Language_Key, "ar");
                editor.commit();
                getActivity().recreate();
                break;
        }
        return super.onContextItemSelected(item);
    }

}
