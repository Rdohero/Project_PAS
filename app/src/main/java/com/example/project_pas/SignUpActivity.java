package com.example.project_pas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.project_pas.databinding.ActivitySignUpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private String fullname, username, email, password, confirmPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = binding.edFullname.getText().toString();
                username = binding.edUsername.getText().toString();
                email = binding.edEmail.getText().toString();
                password = binding.edPassword.getText().toString();
                confirmPassword = binding.edConfirmPassword.getText().toString();

                if (formIsEmpty()) {
                    toastMessage(getString(R.string.error_field_required));
                } else if(!isUsernameValid(username)) {
                    toastMessage(getString(R.string.error_valid_username));
                }else if(!isEmailValid(email)) {
                    toastMessage(getString(R.string.error_valid_email));
                }else if(!isMinimalPasswordValid(password) || !isMinimalPasswordValid(confirmPassword)){
                    toastMessage(getString(R.string.error_minimal_password));
                }else if(!isPasswordMatch()){
                    toastMessage(getString(R.string.error_password_dont_match));
                }else{

                    //SignUp  Process
                    signUpProcess();

                }
            }
        });
    }

    private void toastMessage(String value){
        Toast.makeText(SignUpActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private boolean formIsEmpty() {
        return fullname.isEmpty() ||username.isEmpty() ||email.isEmpty() ||password.isEmpty() || confirmPassword.isEmpty();
    }

    private boolean isUsernameValid(String value){
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return pattern.matcher(value)
                .matches();
    }

    private boolean isEmailValid(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value)
                .matches();
    }

    private boolean isMinimalPasswordValid(String value){
        return value.length() >= 8;
    }

    private boolean isPasswordMatch(){
        return password.equals(confirmPassword);
    }

    private void signUpProcess(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = service.registerUser(username,fullname,email,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                String res;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                if(res != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(res);
                        Boolean status = jsonResponse.getBoolean("status");
                        String mesage = jsonResponse.getString("message");

                        toastMessage(mesage);
                        if(status == true){
                            goToLoginActivity();
                        }else {
                        }


                    } catch (JSONException e) {
                        throw new RuntimeException();
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

    private void goToLoginActivity(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}