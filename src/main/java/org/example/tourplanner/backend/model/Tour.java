package org.example.tourplanner.backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tour {
    private Long id;
    private String name;
    private String description;

    public Tour() {}

    public Tour(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
