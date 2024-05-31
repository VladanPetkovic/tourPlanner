package org.example.tourplanner.frontend.model;

public enum Popularity {
    UNKNOWN,
    UNPOPULAR,
    AVERAGE,
    POPULAR,
    VERY_POPULAR;

    public static Popularity fromInteger(int value) {
        return switch (value) {
            case 0 -> UNKNOWN;
            case 1 -> UNPOPULAR;
            case 2 -> AVERAGE;
            case 3 -> POPULAR;
            default -> VERY_POPULAR;
        };
    }
}
