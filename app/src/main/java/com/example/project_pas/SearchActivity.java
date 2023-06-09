package com.example.project_pas;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project_pas.api.ApiClient;
import com.example.project_pas.api.ApiService;
import com.example.project_pas.databinding.ActivitySearchBinding;
import com.example.project_pas.response.MealsItem;
import com.example.project_pas.response.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchView searchView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }



//        binding.back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SearchActivty.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });



        binding.rvFood.setLayoutManager(new GridLayoutManager(this,2));
        searchView=findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()){
                    MealsAdapter adapter = (MealsAdapter) binding.rvFood.getAdapter();
                    if (adapter != null){
                        adapter.clearData();
                    }
                }else{
                    getSearchMakanan(query);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    MealsAdapter adapter = (MealsAdapter) binding.rvFood.getAdapter();
                    if (adapter != null){
                        adapter.clearData();
                    }
                }else{
                    getSearchMakanan(newText);
                }

                return true;
            }
        });

    }


    private void getSearchMakanan(String s) {
        ApiService ApiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Response> call = ApiService.getSearchMakanan(s);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<MealsItem> makanans = response.body().getMeals();
                if (makanans != null){
                    MealsAdapter adapter = new MealsAdapter(makanans);
                    binding.rvFood.setAdapter(adapter);

                }else {
                    MealsAdapter adapter = (MealsAdapter) binding.rvFood.getAdapter();
                    if (adapter != null) {
                        adapter.clearData();
                    }
                    searchView.clearFocus();
                    Toast.makeText(SearchActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}