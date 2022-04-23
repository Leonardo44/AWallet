package com.llopez.awallet.ui.on_boarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.llopez.awallet.R;

import org.w3c.dom.Text;

public class SliderOnBoardingFragment extends Fragment {
    ImageView imageView;
    ConstraintLayout constraintLayout;
    TextView mainLabel;
    TextView secondLabel;
    Button startButton;

    private int color;
    private Integer image;
    private Integer title;
    private Integer content;
    private boolean isLastItem;

    public SliderOnBoardingFragment(int color, Integer image, Integer title, Integer content, boolean isLastItem) {
        this.color = color;
        this.image = image;
        this.title = title;
        this.content = content;
        this.isLastItem = isLastItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_slider_on_boarding, container, false);
        constraintLayout = layout.findViewById(R.id.layout_container);
        imageView = layout.findViewById(R.id.on_boarding_image);
        mainLabel = layout.findViewById(R.id.on_boarding_title);
        secondLabel = layout.findViewById(R.id.on_boarding_section);
        startButton = layout.findViewById(R.id.btnStart);

        startButton.setOnClickListener(v -> {
            endActivity();
        });

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        constraintLayout.setBackgroundColor(color);
        imageView.setImageResource(image);
        mainLabel.setText(title);
        secondLabel.setText(content);

        if (isLastItem) {
            startButton.setVisibility(View.VISIBLE);
        } else {
            startButton.setVisibility(View.GONE);
        }
    }

    private void endActivity() {
        getActivity().finish();
    }
}