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
        this.Kids = new ListSE();

        this.Kids.add(new Kid("123", "Carlos", (byte) 4));
        this.Kids.add(new Kid("256", "Mariana", (byte) 3));
        this.Kids.add(new Kid("789", "Daniel", (byte) 5));

        Kids.addToStart(new Kid("967", "Estefania", (byte) 6));
        //Kids.addKidPos(5,new Kid("4","Juan",(byte) 10));
    }
    public Node getKids() {
        return this.Kids.getHead();
    }
    public Node addKidPos(int pos, Kid kid) {
        return this.Kids.addKidPos(pos, kid);
    }

    public void invert(){
        Kids.invert();
    }

    public void sendKidFinal(char let){
        Kids.SendKidFinalByLetter(let);
    }

} //FIN C_ListSEService
