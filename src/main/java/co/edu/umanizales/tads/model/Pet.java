package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String name;
    private int age;
    private String identification;
    private String TypePet;
    private String breed;  //(raza)
    private char gender;
    private Location location;

}