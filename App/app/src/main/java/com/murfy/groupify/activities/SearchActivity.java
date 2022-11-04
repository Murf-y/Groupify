package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.adapters.GroupAdapter;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupApi;
import com.murfy.groupify.api.SearchApi;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivitySearchBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        new SearchApi(getApplicationContext()).getRecentSearch(currentUser.getId(), new CrudCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> strings) {
                if(strings.size() ==0 ){
                    binding.recentText.setText("No recent search");
                    binding.recentText.setVisibility(View.VISIBLE);
                    binding.recentSearchesList.setVisibility(View.GONE);
                }else{
                    binding.recentText.setText("Recent");
                    binding.recentText.setVisibility(View.VISIBLE);
                    binding.recentSearchesList.setVisibility(View.VISIBLE);
                    binding.recentSearchesList.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, strings));
                    binding.recentSearchesList.setOnItemClickListener((adapterView, view, i, l) -> {
                        binding.searchBar.setText(strings.get(i));
                        searchForTerm();
                    });
                }
            }

            @Override
            public void onError(CrudError error) {
                binding.recentText.setText("No recent search");
                binding.recentText.setVisibility(View.VISIBLE);
                binding.recentSearchesList.setVisibility(View.GONE);
            }
        });
        binding.searchBar.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                if (target == DrawablePosition.LEFT) {
                    searchForTerm();
                }
            }
        });

        binding.searchBar.setOnKeyListener((view, i, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                    (i == KeyEvent.KEYCODE_ENTER)) {
                searchForTerm();
                return true;
            }
            return false;
        });

        binding.homeButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        });

        binding.backArrow.setOnClickListener(view -> {
            finish();
        });
        binding.addButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), CreateGroupActivity.class);
            startActivity(i);
        });

    }

    private void searchForTerm() {
        String searchTerm = binding.searchBar.getText().toString();
        if(searchTerm.length() <=1 ) return;
        addSearchToRecentSearches(searchTerm);
        new GroupApi(getApplicationContext()).searchForGroups(searchTerm, new CrudCallback<ArrayList<Group>>() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                if(groups.size() == 0){
                    binding.emptyStateImage2.setVisibility(View.VISIBLE);
                    binding.emptyStateText2.setVisibility(View.VISIBLE);
                    binding.recentSearchesList.setVisibility(View.GONE);
                    binding.recentText.setVisibility(View.GONE);
                }else{
                    binding.emptyStateImage2.setVisibility(View.GONE);
                    binding.emptyStateText2.setVisibility(View.GONE);
                    binding.recentSearchesList.setVisibility(View.VISIBLE);
                    binding.recentText.setText("Result");
                    binding.recentText.setVisibility(View.VISIBLE);
                    binding.recentSearchesList.setAdapter(new GroupAdapter(getApplicationContext(), R.layout.group_list_item, groups));
                }
            }

            @Override
            public void onError(CrudError error) {
                binding.emptyStateImage2.setVisibility(View.GONE);
                binding.emptyStateText2.setVisibility(View.GONE);
                binding.recentSearchesList.setVisibility(View.GONE);
                binding.recentText.setText("Something went wrong, try again later . . .");
                binding.recentText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addSearchToRecentSearches(String searchTerm) {
        new SearchApi(getApplicationContext()).postRecentSearch(currentUser.getId(), searchTerm, new CrudCallback<Object>() {
            @Override
            public void onSuccess(Object obj) {
                return;
            }

            @Override
            public void onError(CrudError error) {
                return;
            }
        });
    }
}