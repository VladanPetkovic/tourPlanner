package org.example.tourplanner.frontend.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class Log {
    private String username;
    private LocalDate dateTime;
    private String comment;
    private Integer difficulty; // only in ranges from 1 to 10
    private Double totalDistance;
    private Integer totalTime;
    private Integer rating; // only in ranges from 1 to 5

    public Log(String username, String dateTime, String comment, Integer difficulty,
               Double totalDistance, Integer totalTime, Integer rating) {
        setUsername(username);
        setDateTime(LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setComment(comment);
        setDifficulty(difficulty);
        setTotalDistance(totalDistance);
        setTotalTime(totalTime);
        setRating(rating);
    }

    public Log(Log other) {
        this.username = other.username;
        this.dateTime = other.dateTime;
        this.comment = other.comment;
        this.difficulty = other.difficulty;
        this.totalDistance = other.totalDistance;
        this.totalTime = other.totalTime;
        this.rating = other.rating;
    }


    /**
     * This function checks the submitted string for correct date-input. Only yyyy-MM-dd accepted.
     * @param dateTime The String, that is being checked.
     * @return True, when the string matches the pattern, and returns false, when something else was submitted.
     */
    public static boolean checkDate(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Try parsing the input string using the first pattern
            LocalDate parsedDate = LocalDate.parse(dateTime, formatter);

            if (parsedDate.isAfter(LocalDate.now())) {
                // Date is in the future
                return false;
            } else {
                // Return true if parsing succeeds
                // AND: Date is not in the future
                return true;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
