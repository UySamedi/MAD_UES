package com.example.uesapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.uesapp.Departments.BIOActivity;
import com.example.uesapp.Departments.DEEActivity;
import com.example.uesapp.Departments.ITEActivity;
import com.example.uesapp.Departments.SCAActivity;
import com.example.uesapp.Departments.TEEActivity;
import com.example.uesapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        // Image slider setup
        ImageSlider imageSlider = view.findViewById(R.id.image_Slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.fe_poster1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.fe_poster2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.fe_poster3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Find CardViews
        CardView scaCard = view.findViewById(R.id.scaCard);
        CardView iteCard = view.findViewById(R.id.iteCard);
        CardView bioCard = view.findViewById(R.id.bioCard);
        CardView teeCard = view.findViewById(R.id.teeCard);
        CardView deeCard = view.findViewById(R.id.deeCard);

        // Set click listeners for each CardView
        scaCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SCAActivity.class);
            startActivity(intent);
        });

        iteCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ITEActivity.class);
            startActivity(intent);
        });

        bioCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BIOActivity.class);
            startActivity(intent);
        });

        teeCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TEEActivity.class);
            startActivity(intent);
        });

        deeCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DEEActivity.class);
            startActivity(intent);
        });

        return view;
    }
}