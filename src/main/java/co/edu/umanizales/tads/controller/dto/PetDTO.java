package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PetDTO {

    @NotBlank(message = "No puede estar vacio")
    private String Identification;

    @NotBlank(message = "No puede estar vacio")
    private String name;

    @NotBlank(message = "No puede estar vacio")
    private byte age;

    @NotBlank(message = "No puede estar vacio")
    private String petType;
    @NotBlank(message = "No puede estar vacio")
    private String breed; //(raza)

    @NotBlank(message = "No puede estar vacio")
    private String codeLocation;
    @NotBlank(message = "No puede estar vacio")
    private char gender;
}
