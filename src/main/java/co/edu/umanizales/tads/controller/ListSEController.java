package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.services.ListaSEService;
import co.edu.umanizales.tads.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {

    @Autowired
    private ListaSEService listaSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
            return new ResponseEntity<>(new ResponseDTO(200,listaSEService.getKids().getHead(),null), HttpStatus.OK); }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        Kid kidExisting = listaSEService.getKids().getKidByIdentification(kidDTO.getIdentification());
        if (kidExisting != null){
            return new ResponseEntity<>(new ResponseDTO(409,"Ya existe un niño con la misma identificacion",null), HttpStatus.CONFLICT);
        }
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404,"La ubicación no existe",
                    null), HttpStatus.OK);
        }
        listaSEService.getKids().add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el petacón", null), HttpStatus.OK);

    }

//-------------------- PROTOTIPOS DE METODOS DE EXAMEN EN EL CONTROLLER DE LA ListSE //

    @GetMapping(path = "/invertir_list")
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
            listaSEService.getKids().SendKidFinalByLetter(code);
            return new ResponseEntity<>(new ResponseDTO(200,"La lista se ha organizado",null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409, "No existen niños, por lo tanto no se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtreme() {
        listaSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(200, "Se han intercambiado los extremos", null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
        public ResponseEntity<ResponseDTO> getKidsByLocation(){
            List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
            for(Location loc: locationService.getLocations()){
                int count = listaSEService.getKids().getCountKidsByLocationCode(loc.getCode());
                if(count>0){
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200,kidsByLocationDTOList,
                    null), HttpStatus.OK);
        }

}//fin c_Controller