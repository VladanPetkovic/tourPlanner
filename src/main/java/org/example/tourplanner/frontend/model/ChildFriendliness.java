package org.example.tourplanner.frontend.model;

public enum ChildFriendliness {
    VERY_UNFRIENDLY,
    UNFRIENDLY,
    NEUTRAL,
    FRIENDLY,
    VERY_FRIENDLY;

    public static ChildFriendliness fromDouble(double value) {
        int roundedValue = (int) Math.round(value);

        if (roundedValue <= 0) {
            return VERY_UNFRIENDLY;
        }
        return switch (roundedValue) {
            case 1 -> UNFRIENDLY;
            case 2 -> NEUTRAL;
            case 3 -> FRIENDLY;
            default -> VERY_FRIENDLY;
        };
    }
}
