package com.hardtask.eid.ahmed.souqtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hardtask.eid.ahmed.souqtask.view.fragments.SubCategoryFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String language;

    public CategoryAdapter(Context mContext, List<Category> categories) {
        this.categories = categories;
        this.mContext = mContext;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        language = sharedPreferences.getString(AppConfig.ShardPreference_Language_Key, "en");
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView;
        myView = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final Category category = categories.get(position);

        Glide.with(mContext)
                .load(category.getPhoto())
                .error(R.drawable.cat_no_img)
                .into(holder.imageView);

        if (language.equals("ar")) {
            holder.textView.setText(category.getTitleAR()+" ( "+category.getProductCount()+" )");
        } else {
            holder.textView.setText(category.getTitleEN()+" ( "+category.getProductCount()+" )");
        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConfig.INTENT_CategoryKey, categories.get(getAdapterPosition()));
            SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
            subCategoryFragment.setArguments(bundle);
            if (language.equals("ar")) {
                fragmentTransaction.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right, R.anim.in_from_right, R.anim.out_to_left);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
            }
            fragmentTransaction.replace(R.id.container_layout, subCategoryFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
