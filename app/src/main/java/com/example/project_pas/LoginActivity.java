package com.example.project_pas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project_pas.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String username, password;
    private ProgressDialog progressDialog;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferences = new Preferences(this);
        if(preferences.getSessionLogin() == true ){
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.edUsername.getText().toString();
                password = binding.edPassword.getText().toString();

                if(formIsEmpty()){
                    Toast.makeText(LoginActivity.this, getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
                }else{
                    //Login Process
                    loginProcess();
                    /*if (username.equals("admin")&& password.equals("admin")) {
                        Toast.makeText(LoginActivity.this, "Login sukses", Toast.LENGTH_SHORT);
                        preferences.setSessionLogin(true);

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);

                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"Login gagal",Toast.LENGTH_SHORT);
                    }*/
                }
            }
        });
    }

    private boolean formIsEmpty(){
        return username.isEmpty() || password.isEmpty();
    }


    private void loginProcess(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        ApiService service = ApiClientLogin.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = service.login(username,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                String res;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if(res != null){
                    try {
                        JSONObject jsonRespones = new JSONObject(res);

                        Boolean status = jsonRespones.getBoolean("status");
                        String message = jsonRespones.getString("message");

                        toastMessage(message);

                        if(status == true) {
                            goToProfileActivity();
                            preferences.setSessionLogin(true);
                            goToProfileActivity();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                toastMessage("Something went wrong...Please try later!");
            }
        });
    }

    private void goToProfileActivity(){
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}