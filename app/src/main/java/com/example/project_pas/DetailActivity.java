package com.example.project_pas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_pas.api.ApiClient;
import com.example.project_pas.api.ApiService;
import com.example.project_pas.databinding.ActivityDetailBinding;
import com.example.project_pas.response.MealsItem;
import com.example.project_pas.response.Response;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String meals = intent.getStringExtra("Seafood");

        getDetailMeals(meals);
    }

    private void getDetailMeals(String mealId) {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Response> call = service.getMealDetail(mealId);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                progressDialog.dismiss();
                Response response1 = response.body();
                MealsItem mealsItem = response1.getMeals().get(0);
                setDataUI(mealsItem);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDataUI(MealsItem meals){
        binding.nameDetail.setText(meals.getStrMeal());
        binding.descriptionDetail.setText(meals.getStrInstructions());
        binding.area.setText(meals.getStrArea());
        binding.kategori.setText(meals.getStrCategory());
        binding.strMeasure1.setText(meals.getStrMeasure1());
        binding.strMeasure2.setText(meals.getStrMeasure2());
        binding.strMeasure3.setText(meals.getStrMeasure3());
        binding.strMeasure4.setText(meals.getStrMeasure4());
        binding.strMeasure5.setText(meals.getStrMeasure5());
        binding.strMeasure6.setText(meals.getStrMeasure6());
        binding.strMeasure7.setText(meals.getStrMeasure7());
        binding.strMeasure8.setText(meals.getStrMeasure8());
        binding.strMeasure9.setText(meals.getStrMeasure9());
        binding.strMeasure10.setText(meals.getStrMeasure10());
        binding.strMeasure11.setText(meals.getStrMeasure11());
        binding.strMeasure12.setText(meals.getStrMeasure12());
        binding.strMeasure13.setText(meals.getStrMeasure13());
        binding.strMeasure14.setText(meals.getStrMeasure14());
        binding.strMeasure15.setText(meals.getStrMeasure15());
        binding.strIngredient1.setText(meals.getStrIngredient1());
        binding.strIngredient2.setText(meals.getStrIngredient2());
        binding.strIngredient3.setText(meals.getStrIngredient3());
        binding.strIngredient4.setText(meals.getStrIngredient4());
        binding.strIngredient5.setText(meals.getStrIngredient5());
        binding.strIngredient6.setText(meals.getStrIngredient6());
        binding.strIngredient7.setText(meals.getStrIngredient7());
        binding.strIngredient8.setText(meals.getStrIngredient8());
        binding.strIngredient9.setText(meals.getStrIngredient9());
        binding.strIngredient10.setText(meals.getStrIngredient10());
        binding.strIngredient11.setText(meals.getStrIngredient11());
        binding.strIngredient12.setText(meals.getStrIngredient12());
        binding.strIngredient13.setText(meals.getStrIngredient13());
        binding.strIngredient14.setText(meals.getStrIngredient14());
        binding.strIngredient15.setText(meals.getStrIngredient15());

        String urlPoster = meals.getStrMealThumb();
        Picasso.get()
                .load(urlPoster)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(binding.imageDetail);
    }
}