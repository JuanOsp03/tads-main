package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.services.ListaSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {

    @Autowired
    private ListaSEService listaSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        if (listaSEService.getKids() != null) {
            return new ResponseEntity<>(new ResponseDTO(200,listaSEService.getKids(),null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409, "No existen niños, por lo tanto no se puede realizar la acción deseada", null),
                    HttpStatus.BAD_REQUEST);
        }
    }
//-------------------- PROTOTIPOS DE METODOS DE EXAMEN EN EL CONTROLLER DE LA ListSE //
    @GetMapping(path = "/invertirList")
    public ResponseEntity<ResponseDTO> invert() {
        if (listaSEService.getKids() != null) {
            listaSEService.invert();
            return new ResponseEntity<>(new ResponseDTO(200, "La lista se ha invertido", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(409, "No existen niños, por lo tanto no se puede realizar la acción", null),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/sendKidFinal/{code}")
    public ResponseEntity<ResponseDTO> SendKidFinalByLetter(@PathVariable char code){  // <-------Este metodo, no me funciona
        if (listaSEService.getKids() != null) {
            listaSEService.sendKidFinal(code);
            return new ResponseEntity<>(new ResponseDTO(200,"La lista se ha organizado",null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409, "No existen niños, por lo tanto no se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
        }
    }

}//fin c_Controller