package co.edu.umanizales.tads.services;

import co.edu.umanizales.tads.model.ListDE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDEService {
    private ListDE pets;
    public ListDEService(){
        pets = new ListDE();
    }
}