package com.example.tripplanner.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.tripplanner.R;
import com.example.tripplanner.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AuthenticationActivity extends AppCompatActivity{
    private UserViewModel userViewModel;

    private LoginFragment loginFragment = new LoginFragment();
    private RegisterFragment registerFragment = new RegisterFragment();

    private ImageView leftBullet;
    private ImageView rightBullet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        leftBullet = findViewById(R.id.left_bullet);
        rightBullet = findViewById(R.id.right_bullet);

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager(), viewPager, leftBullet, rightBullet);
        pagerAdapter.addFragmet(loginFragment);
        pagerAdapter.addFragmet(registerFragment);
        viewPager.setAdapter(pagerAdapter);

    }

    static class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private static final int LOGIN_FRAGMENT = 0;
        private static final int REGISTER_FRAGMENT = 1;

        private ImageView leftBullet;
        private ImageView rightBullet;

        private ArrayList<Fragment> fragmentList = new ArrayList<>();
        private ViewPager viewPager;
        private int previousItem = -1;

        public AuthenticationPagerAdapter(FragmentManager fm, ViewPager viewPager, ImageView leftBullet, ImageView rightBullet) {
            super(fm);
            this.leftBullet = leftBullet;
            this.rightBullet = rightBullet;
            this.viewPager = viewPager;
            previousItem = viewPager.getCurrentItem();
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public void finishUpdate(@NonNull @NotNull ViewGroup container) {
            super.finishUpdate(container);

            if(previousItem != viewPager.getCurrentItem()) {
                previousItem = viewPager.getCurrentItem();
                if(viewPager.getCurrentItem() == LOGIN_FRAGMENT) {
                    leftBullet.setImageResource(R.drawable.ic_active_bullet);
                    rightBullet.setImageResource(R.drawable.ic_disabled_bullet);
                } else if(viewPager.getCurrentItem() == REGISTER_FRAGMENT){
                    leftBullet.setImageResource(R.drawable.ic_disabled_bullet);
                    rightBullet.setImageResource(R.drawable.ic_active_bullet);
                }
            }
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}
