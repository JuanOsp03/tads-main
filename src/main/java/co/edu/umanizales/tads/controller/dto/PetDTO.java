package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PetDTO {

    @NotBlank(message = "No puede estar vacio")
    private String Identification;

    @Size(max = 30 , min = 3, message = "El nombre no puede superar los treinta caracteres y no puede ser menor a tres caracteres")
    @NotBlank(message = "No puede estar vacio")
    private String name;

    @Size(max = 2 , min = 1 , message = "La mascota no puede tener mas de dos caracteres de edad")
    @NotBlank(message = "No puede estar vacio")
    private byte age;

    @NotBlank(message = "No puede estar vacio")
    private String petType;
    @NotBlank(message = "No puede estar vacio")
    private String breed; //(raza)

    @Size(max = 8 , min = 3 , message = "El estandar del codigo de localizacion no puede ser menor a tres caracteres y no puede ser mayor a ocho caracteres")
    @NotBlank(message = "No puede estar vacio")
    private String codeLocation;
    @NotBlank(message = "No puede estar vacio")
    private char gender;
}
