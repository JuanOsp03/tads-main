package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.services.ListaSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListaSEService listaSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listaSEService.getKids(),null), HttpStatus.OK);
    }

}