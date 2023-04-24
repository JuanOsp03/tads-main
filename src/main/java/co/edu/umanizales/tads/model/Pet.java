package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    //atributos de una mascota
    private String name;
    private int age;
    private String breed;  //(raza)
    private char gender;
    /*
    private String identification;
    private Location location;
     */
}