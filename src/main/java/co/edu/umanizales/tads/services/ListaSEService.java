package co.edu.umanizales.tads.services;

import co.edu.umanizales.tads.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListaSEService {
    private ListSE Kids;

    public ListaSEService() {
        this.Kids = new ListSE();
    }

    public void invert(){ Kids.invert();}

    //public void changeExtremes(){ Kids.changeExtremes();}

    //public void sendKidFinal(char let){Kids.SendKidFinalByLetter(let);}

    //public void getCountKidsByLocationCode(String code){ Kids.getCountKidsByLocationCode(code);}

    //public void addKidPos(int pos, Kid kid){ this.Kids.addKidPos(pos, kid);}

} //FIN C_ListSEService

