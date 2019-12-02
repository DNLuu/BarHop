package com.example.BarHop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends Fragment {
    Button back, signUp;
    EditText id, pwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, null);

        id = (EditText)view.findViewById(R.id.input_username);
        pwd = (EditText)view.findViewById(R.id.input_pwd);

        signUp = (Button)view.findViewById(R.id.btn_signup);

        signUp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Account registered.", Toast.LENGTH_SHORT).show();
                String id_pwd = "#Reg#" + id.getText().toString() + "@" + pwd.getText().toString();

                MainActivity.sendMessageToServer(id_pwd, MainActivity.socket);

                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, new login());

                fragmentTransaction.commit();
            }
        }));

        back = (Button)view.findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, new login());

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
