package com.example.tugaspraktikum6;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tugaspraktikum6.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final List<String[]> items = new ArrayList<>();
    private final List<String[]> accounts = new ArrayList<>();
    private final List<String[]> dummyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fragmentManager = getSupportFragmentManager();
        addDummyData();

        if (items.size() == 0){
            binding.fragmentContainer.setVisibility(View.GONE);
            binding.textView.setVisibility(View.VISIBLE);
        } else {
            binding.fragmentContainer.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.GONE);
        }

        HomeFragment homeFragment = new HomeFragment();
        Fragment fragmentHome = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        if (!(fragmentHome instanceof HomeFragment)) {
            fragmentManager
                    .beginTransaction()
                    .replace(binding.fragmentContainer.getId(), homeFragment, HomeFragment.class.getSimpleName())
                    .commit();
        }

        binding.btnHome.setOnClickListener(v -> {
            if (items.size() == 0){
                binding.fragmentContainer.setVisibility(View.GONE);
                binding.textView.setVisibility(View.VISIBLE);
            } else {
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                binding.textView.setVisibility(View.GONE);
            }
            if (!(fragmentHome instanceof HomeFragment)) {
                fragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), homeFragment)
                        .commit();
            }
        });

        SearchFragment searchFragment = new SearchFragment();
        Fragment fragmentSearch = fragmentManager.findFragmentByTag(SearchFragment.class.getSimpleName());
        binding.btnSearch.setOnClickListener(v -> {
            binding.fragmentContainer.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.GONE);
            if (!(fragmentSearch instanceof SearchFragment)) {
                fragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), searchFragment)
                        .commit();
            }
        });

        AddFragment addFragment = new AddFragment();
        Fragment fragmentAdd = fragmentManager.findFragmentByTag(AddFragment.class.getSimpleName());
        binding.btnAdd.setOnClickListener(v -> {
            binding.fragmentContainer.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.GONE);
            if (!(fragmentAdd instanceof AddFragment)) {
                fragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), addFragment)
                        .commit();
            }
        });

        ProfileFragment profileFragment = new ProfileFragment();
        Fragment fragmentProfile = fragmentManager.findFragmentByTag(ProfileFragment.class.getSimpleName());
        binding.btnProfile.setOnClickListener(v -> {
            binding.fragmentContainer.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.GONE);
            if (!(fragmentProfile instanceof ProfileFragment)) {
                fragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), profileFragment)
                        .commit();
            }
        });

    }

    public void addPost(String username, String name, Uri uriProfile, Uri uriImage, String caption){
        String[] post = {username, name, String.valueOf(uriProfile), String.valueOf(uriImage), caption};
        items.add(0,post);
    }

    public List<String[]> getItems() {
        return items;
    }

    public void addDummyData(){
        items.add(new String[]{
                "@balhensem123",
                "BalmondTzy",
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.balmond))
                        .appendPath(getResources().getResourceTypeName(R.drawable.balmond))
                        .appendPath(getResources().getResourceEntryName(R.drawable.balmond))
                        .build()),
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.balmond2))
                        .appendPath(getResources().getResourceTypeName(R.drawable.balmond2))
                        .appendPath(getResources().getResourceEntryName(R.drawable.balmond2))
                        .build()),
                "呀哈哈，有没有报应导师？"});
        items.add(new String[]{
            "@satsetsatset3m",
            "Lance Tiktok",
            String.valueOf((new Uri.Builder())
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(getResources().getResourcePackageName(R.drawable.lancelot))
                    .appendPath(getResources().getResourceTypeName(R.drawable.lancelot))
                    .appendPath(getResources().getResourceEntryName(R.drawable.lancelot))
                    .build()),
            String.valueOf((new Uri.Builder())
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(getResources().getResourcePackageName(R.drawable.lance2))
                    .appendPath(getResources().getResourceTypeName(R.drawable.lance2))
                    .appendPath(getResources().getResourceEntryName(R.drawable.lance2))
                    .build()),
            "just wanna be ur royal matador :(("});
        items.add(new String[]{
                "@majulodunia007",
                "Argus Kasim",
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.argus))
                        .appendPath(getResources().getResourceTypeName(R.drawable.argus))
                        .appendPath(getResources().getResourceEntryName(R.drawable.argus))
                        .build()),
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.argus1))
                        .appendPath(getResources().getResourceTypeName(R.drawable.argus1))
                        .appendPath(getResources().getResourceEntryName(R.drawable.argus1))
                        .build()),
                "Yang tau-tau aja :)"});
        items.add(new String[]{
                "@balhensem123",
                "BalmondTzy",
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.balmond))
                        .appendPath(getResources().getResourceTypeName(R.drawable.balmond))
                        .appendPath(getResources().getResourceEntryName(R.drawable.balmond))
                        .build()),
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.balmond1))
                        .appendPath(getResources().getResourceTypeName(R.drawable.balmond1))
                        .appendPath(getResources().getResourceEntryName(R.drawable.balmond1))
                        .build()),
                "Selain tampan, rupawan, pemberani, serta baik hati, aku juga jago main basket :D"});
        items.add(new String[]{
                "@gojo_wannabe",
                "Xavier Satoru",
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.xavier))
                        .appendPath(getResources().getResourceTypeName(R.drawable.xavier))
                        .appendPath(getResources().getResourceEntryName(R.drawable.xavier))
                        .build()),
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.xavier1))
                        .appendPath(getResources().getResourceTypeName(R.drawable.xavier1))
                        .appendPath(getResources().getResourceEntryName(R.drawable.xavier1))
                        .build()),
                "Watashi sebenarnya tidak ingin menjadi sorcerer terkuat di dunia, tapi karena aku terlahir dengan kekuatan yang luar biasa, aku tidak punya pilihan lain selain menjadi sorcerer terkuat di dunia :v"});
        items.add(new String[]{
                "@satsetsatset3m",
                "Lance Tiktok",
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.lancelot))
                        .appendPath(getResources().getResourceTypeName(R.drawable.lancelot))
                        .appendPath(getResources().getResourceEntryName(R.drawable.lancelot))
                        .build()),
                String.valueOf((new Uri.Builder())
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(R.drawable.lance1))
                        .appendPath(getResources().getResourceTypeName(R.drawable.lance1))
                        .appendPath(getResources().getResourceEntryName(R.drawable.lance1))
                        .build()),
                "-1 lovers :("});
    }

    public List<String[]> getAccounts() {
        filterAccounts();
        return accounts;
    }

    public void copyAccounts(){
        for (int i = 0; i < items.size(); i++){
            dummyList.add(new String[]{
                    items.get(i)[0],
                    items.get(i)[1],
                    items.get(i)[2]
            });
        }
    }

    public void filterAccounts() {
        copyAccounts();
        for (int i = 0; i < dummyList.size(); i++) {
            boolean found = false;
            for (int j = 0; j < accounts.size(); j++) {
                if (Objects.equals(accounts.get(j)[0], dummyList.get(i)[0])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                accounts.add(dummyList.get(i));
            }
        }
    }


}