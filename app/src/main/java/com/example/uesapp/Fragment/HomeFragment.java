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
import com.example.uesapp.R;

import java.util.ArrayList;

//import com.example.my_app.Departments.ITEActivity;

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


        return view;
    }
}
