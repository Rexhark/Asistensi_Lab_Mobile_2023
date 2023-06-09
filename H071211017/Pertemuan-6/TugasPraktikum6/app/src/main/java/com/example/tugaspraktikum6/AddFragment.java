package com.example.tugaspraktikum6;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tugaspraktikum6.databinding.FragmentAddBinding;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {
    FragmentAddBinding binding;
    private Uri uriGambar;
    List<String[]> items = new ArrayList<>();
    ActivityResultLauncher<Intent> imagePicker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getData() != null) {
                        uriGambar = result.getData().getData();
                        if (uriGambar != null) {
                            Glide.with(requireActivity())
                                    .load(uriGambar)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(binding.gambarPost);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater,container,false);
        items = ((MainActivity) requireActivity()).getItems();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.gambarPost.setOnClickListener(v -> pilihGambar());
        binding.btnUpload.setOnClickListener(v -> upload());

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.captionPost.setText("");
    }

    private void upload() {

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        String caption = binding.captionPost.getText().toString();
        Uri profileUri = (new Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.black_androidpng))
                .appendPath(getResources().getResourceTypeName(R.drawable.black_androidpng))
                .appendPath(getResources().getResourceEntryName(R.drawable.black_androidpng))
                .build();

        if (uriGambar != null) {
            ((MainActivity) requireActivity()).addPost("ramafitrahramadhan", "Fitrah Ramadhan", profileUri, uriGambar, caption);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, homeFragment, HomeFragment.class.getSimpleName())
                    .commit();
            uriGambar = null;
        } else {
            Toast.makeText(getActivity(), "Upload gambar post terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    public void pilihGambar() {
        Intent imagePickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePicker.launch(Intent.createChooser(imagePickerIntent,"Pilih Gambar"));
    }

}