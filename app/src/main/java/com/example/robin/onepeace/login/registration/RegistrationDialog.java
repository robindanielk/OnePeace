package com.example.robin.onepeace.login.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robin.onepeace.R;
import com.example.robin.onepeace.helper.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationDialog extends DialogFragment {

    private static final String TAG = RegistrationDialog.class.getSimpleName();

    @BindView(R.id.newEmailEt)
    EditText email;

    @BindView(R.id.newPasswordEt)
    EditText passWord;

    @BindView(R.id.registerBtn)
    Button registerButton;

    @BindView(R.id.cancelBtn)
    Button cancelBtn;


    public static RegistrationDialog newInstance() {

        Bundle args = new Bundle();
        RegistrationDialog fragment = new RegistrationDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_dialog, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String emailStr = email.getText().toString();
        String passWordStr = passWord.getText().toString();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isValidCredentials(emailStr,passWordStr)){
                    FirebaseAuth auth = FirebaseAuth.getInstance();
//            mViewModel.registerUser(auth,getActivity(),email,passWord);
                    createUser(auth,emailStr,passWordStr);
                }else{
                    showToast(R.string.invalid_input);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void createUser(FirebaseAuth auth, String emailStr, String passWordStr) {
        auth.createUserWithEmailAndPassword(emailStr,passWordStr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            showToast(R.string.registration_success);
                            dismiss();
                        }else{
                            Exception exception = task.getException();
                            if(exception instanceof FirebaseAuthUserCollisionException){
                                showToast(R.string.email_already_registered);
                            }
                            showToast(R.string.registration_failed);
                        }
                    }
                });
    }


    public void show(FragmentManager manager){
        super.show(manager,TAG);
    }

    private void showToast(@StringRes int string){
        String message = getActivity().getString(string);
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
