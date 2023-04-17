package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Kid {
    private String Indentification;
    private String name;
    private byte age;
    private char gender;
    private Location location;
}
