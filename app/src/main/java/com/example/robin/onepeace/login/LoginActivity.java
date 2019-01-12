package com.example.robin.onepeace.login;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robin.onepeace.R;
import com.example.robin.onepeace.helper.CommonUtils;
import com.example.robin.onepeace.helper.NetworkUtils;
import com.example.robin.onepeace.login.registration.RegistrationDialog;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
     EditText password;

    @BindView(R.id.loginBtn)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
//        email = ((EditText) findViewById(R.id.email));
//        password = ((EditText) findViewById(R.id.password));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailStr = email.getText().toString();
        String passWordStr = password.getText().toString();
        if (NetworkUtils.checkConnection()){
            if (CommonUtils.isValidCredentials(emailStr,passWordStr)) {
                auth.signInWithEmailAndPassword(emailStr, passWordStr)
                        .addOnCompleteListener(this, task -> {
                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                   showMessage(R.string.minimum_password);
                                } else {
                                    System.out.println("***" + task.getException().getMessage() + "***");
                                    showMessage(R.string.auth_failed);
                                }
                            } else {
                                openDialogActivity();
                            }
                        });

            }else{
                showMessage(R.string.invalid_input);
            }
        }else {
            showMessage(R.string.no_internet);
//        }
        }
    }

    private void openDialogActivity() {
        RegistrationDialog.newInstance().show(getSupportFragmentManager());
    }

    private void showMessage(@StringRes int message){
        Toast.makeText(this,getString(message),Toast.LENGTH_SHORT).show();
    }

}
