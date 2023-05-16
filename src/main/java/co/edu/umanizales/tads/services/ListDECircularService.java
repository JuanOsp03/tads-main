package co.edu.umanizales.tads.services;

import co.edu.umanizales.tads.model.ListDECircular;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ListDECircularService {
    private ListDECircular petsCircular;
    public ListDECircularService() { this.petsCircular = new ListDECircular(); }
}
