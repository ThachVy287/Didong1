package com.example.hoangthachvy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hoangthachvy.R;
import com.example.hoangthachvy.databinding.ActivityMainBinding;
import com.example.hoangthachvy.fragments.AccountFragment;
import com.example.hoangthachvy.fragments.CartFragment;
import com.example.hoangthachvy.fragments.HomeFragment;
import com.example.hoangthachvy.interfaces.CartTotalListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements CartTotalListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.hoangthachvy.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragments(new HomeFragment());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                if(item.getItemId() == R.id.home) {
                    replaceFragments(new HomeFragment());
                } else if (item.getItemId() == R.id.cart) {
                    replaceFragments(new CartFragment());
                } else if (item.getItemId() == R.id.account) {
                    replaceFragments(new AccountFragment());
                }
                return true;
            });
        }

    }

    private void replaceFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_start, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onCartTotalUpdated(double total) {
        Log.d("Cart Total", "Updated total: " + total);
    }
}