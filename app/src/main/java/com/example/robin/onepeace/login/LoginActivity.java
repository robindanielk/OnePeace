package com.example.robin.onepeace.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.robin.onepeace.R;
import com.example.robin.onepeace.helper.CommonUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = ((EditText) findViewById(R.id.email));
        password = ((EditText) findViewById(R.id.password));
    }

    public void login() {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String email = mActivityLoginBinding.email.getText().toString();
//        String passWord = mActivityLoginBinding.password.getText().toString();
//        if (isNetworkConnected()){
//            if (CommonUtils.isValidCredentials(email,passWord)){
//                mLoginViewModel.login(auth,this,email,passWord);
//            }else{
//                showError(R.string.invalid_input);
//            }
//        }else{
//            showError(R.string.no_internet);
//        }

    }

}
