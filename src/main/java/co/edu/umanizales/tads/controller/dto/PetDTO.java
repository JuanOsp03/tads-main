package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PetDTO {

    @NotBlank(message = "No puede estar vacio")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
    private String Identification;
    @NotBlank(message = "No puede estar vacio")
    private String name;
    @Min(value = 1)
    @Max(value = 14)
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
    @NotBlank(message = "No puede estar vacio")
    private  boolean bathed;
}
