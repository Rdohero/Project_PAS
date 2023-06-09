package com.example.project_pas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.project_pas.api.ApiClient;
import com.example.project_pas.api.ApiService;
import com.example.project_pas.databinding.ActivityHomeBinding;
import com.example.project_pas.response.MealsItem;
import com.example.project_pas.response.Response;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvFood.setLayoutManager(new GridLayoutManager(this,2));

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        getMealDb();
    }

    private void getMealDb(){
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        //TODO
        //Get Data From API
        /*Create handle for the RetrofitInstance interface*/
        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Response> call = service.getMealDb("Seafood");
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDialog.dismiss();
                List<MealsItem> meals = response.body().getMeals();
                MealsAdapter adapter = new MealsAdapter(meals);
                binding.rvFood.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}