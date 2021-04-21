package com.seiko.anylayer.internal.util;

public class Utils {

    public static float floatRange01(float value) {
        return floatRange(value, 0F, 1F);
    }

    public static float floatRange(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static int intRange(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
