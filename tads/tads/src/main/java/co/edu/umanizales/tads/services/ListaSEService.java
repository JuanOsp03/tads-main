package co.edu.umanizales.tads.services;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListaSEService {
    private ListSE Kids;

    public ListaSEService() {
        Kids = new ListSE();
        Kids.add(new Kid("123","Carlos",(byte)4));
        Kids.add(new Kid("256","Mariana",(byte)3));
        Kids.add(new Kid("789","Daniel",(byte)5));

        Kids.addToStart(new Kid("967","Estefania",(byte)6));
        Kids.addKidPos(2,new Kid("459","Ximena",(byte)10));
        Kids.DeleteKidByIdentification("123");
    }

    public Node getKids()
    {
        return Kids.getHead();
    }

}
