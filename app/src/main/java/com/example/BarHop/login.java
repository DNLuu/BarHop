package com.example.BarHop;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends Fragment {

    TextView textView;
    Button register, login;
    EditText id, pwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login,null);

        id = (EditText)view.findViewById(R.id.input_id);
        pwd = (EditText)view.findViewById(R.id.input_pwd);

        register = (Button)view.findViewById(R.id.btn_register);
        login=(Button)view.findViewById(R.id.btn_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, new register());

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_pwd="#470#" + id.getText().toString() + "@" +
                                pwd.getText().toString();

                MainActivity.sendMessageToServer(id_pwd, MainActivity.socket);
            }
        });

        return view;
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}