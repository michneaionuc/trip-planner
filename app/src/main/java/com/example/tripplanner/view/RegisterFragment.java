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
import com.example.tripplanner.model.User;
import com.example.tripplanner.viewmodel.UserViewModel;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private View view;

    private UserViewModel userViewModel;

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRePassword;
    private Button buttonRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);
        editTextFirstName = view.findViewById(R.id.et_first_name);
        editTextLastName = view.findViewById(R.id.et_last_name);
        editTextEmail = view.findViewById(R.id.et_email);
        editTextPassword = view.findViewById(R.id.et_password);
        editTextRePassword = view.findViewById(R.id.et_repassword);
        buttonRegister = view.findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);
    }

    private void saveUser() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String rePassword = editTextRePassword.getText().toString();

        // no regex, only non empty fields
        if (firstName.trim().isEmpty()
                || lastName.trim().isEmpty()
                || email.trim().isEmpty()
                || password.trim().isEmpty()
                || password.trim().isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        if(userViewModel.getUserByEmail(email) != null) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.user_exists), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.trim().equals(rePassword.trim())) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.passwords_not_match), Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.insert(new User(firstName, lastName, email, password));
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.register_successfully), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                saveUser();
                break;
        }
    }
}
