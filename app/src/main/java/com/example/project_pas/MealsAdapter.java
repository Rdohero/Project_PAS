package com.example.project_pas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_pas.response.MealsItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private List<MealsItem> localDataSet;

    public MealsAdapter(List<MealsItem> dataSet) {
        localDataSet = dataSet;
    }

    public void clearData() {
        localDataSet.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMealsTitle;
        private final ImageView imgMealsImage;

        public ViewHolder(View view) {
            super(view);

            tvMealsTitle = view.findViewById(R.id.Meal_name);
            imgMealsImage = view.findViewById(R.id.Meal_image);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        MealsItem meals = localDataSet.get(position);
        String title = meals.getStrMeal();
        String urlImage = meals.getStrMealThumb();

        viewHolder.tvMealsTitle.setText(title);

        Picasso.get()
                .load(urlImage)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(viewHolder.imgMealsImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("Seafood",String.valueOf(meals.getIdMeal()));
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

