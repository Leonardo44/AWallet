package com.llopez.awallet.utilities;

import com.llopez.awallet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Color {
    public int index;
    public String name;
    public int color;

    public static ArrayList<Color> CategoryColorList = new ArrayList<>(Arrays.asList(new Color(0, "Negro", R.color.category_first_color), new Color(1, "Azul", R.color.category_second_color), new Color(2,"Naranja", R.color.category_third_color), new Color(3, "Verde", R.color.category_fourth_color), new Color(4, "Rosa", R.color.category_fifth_color), new Color(5, "Rojo", R.color.category_sixth_color), new Color(6, "Gris", R.color.category_seventh_color), new Color(7, "Amarillo", R.color.category_eighth_color)));

    public Color(int index, String name, int color) {
        this.index = index;
        this.name = name;
        this.color = color;
    }
}


