package com.ram.hari.android.bitsniteeats;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MessHome extends Fragment {

    Spinner spinner;
    ArrayList<String> Mess;
    ArrayAdapter<String> arrayAdapter;
    String selected;
    Button next;

    Button signout;
    FirebaseAuth mAuth;

    public MessHome() {
    }

    public static MessHome newInstance(String param1, String param2) {
        MessHome fragment = new MessHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mess_home, container, false);
        spinner = view.findViewById(R.id.Mess_spinner);
        next = view.findViewById(R.id.Gobtn);
        signout = view.findViewById(R.id.signout);
        mAuth = FirebaseAuth.getInstance();
        Mess = new ArrayList<>();
        Mess.add("A Mess");
        Mess.add("C Mess");
        Mess.add("D Mess");
        arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,Mess);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),selected,Toast.LENGTH_SHORT).show();
                Intent openMenu = new Intent(getActivity(),MessMenuActivity.class);
                openMenu.putExtra("mess",selected);
                openMenu.putExtra("Email",getActivity().getIntent().getStringExtra("Email"));
                startActivity(openMenu);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent signOut = new Intent(getActivity(), SignIn.class);
                startActivity(signOut);
                getActivity().finishAffinity();
            }
        });


        return view;
    }

}

