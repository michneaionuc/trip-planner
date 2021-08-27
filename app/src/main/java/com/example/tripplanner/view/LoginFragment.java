package com.example.tripplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tripplanner.R;
import com.example.tripplanner.viewmodel.UserViewModel;

import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private View view;

    private UserViewModel userViewModel;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);
        editTextEmail = view.findViewById(R.id.et_email);
        editTextPassword = view.findViewById(R.id.et_password);
        buttonLogin = view.findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(this);
    }

    public void loginUser() throws ExecutionException, InterruptedException {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        Toast.makeText(getActivity().getApplicationContext(), userViewModel.getLocations().get(), Toast.LENGTH_LONG).show();
//         no regex, only non empty fields
        if ( email.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.empty_fields), Toast.LENGTH_LONG).show();
            return;
        }

        if(userViewModel.getUserByEmail(email) == null) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.incorrect_email), Toast.LENGTH_LONG).show();
            editTextEmail.setText("");
            editTextEmail.requestFocus();
            return;
        }

        if(!userViewModel.getUserByEmail(email).getPassword().equals(password)) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.incorrect_password), Toast.LENGTH_LONG).show();
            editTextPassword.setText("");
            editTextPassword.requestFocus();
            return;
        }

        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.login_successfully), Toast.LENGTH_LONG).show();
        editTextEmail.setText("");
        editTextPassword.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                try {
                    loginUser();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
