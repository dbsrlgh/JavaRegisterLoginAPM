package com.example.javaregisterlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javaregisterlogin.Common.Common;
import com.example.javaregisterlogin.Model.APIResponse;
import com.example.javaregisterlogin.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView txt_sign_in;
    EditText edt_email, edt_password, edt_name;
    Button btn_register;
    IMyAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Init Service
        mService = Common.getAPI();

        //Init View
        txt_sign_in = (TextView) findViewById(R.id.txt_login);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        //Event
        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUser(edt_name.getText().toString(), edt_email.getText().toString(), edt_password.getText().toString());
            }
        });
    }


    private void createNewUser(String name, String email, String password) {

        mService.registerUser(name, email, password)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                        APIResponse result = response.body();

                        if(result.isError()){
                            Toast.makeText(RegisterActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(RegisterActivity.this, "User created : "+result.getUid(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
