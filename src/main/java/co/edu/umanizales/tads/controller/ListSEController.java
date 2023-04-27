package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListSEException;
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
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(404,"La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listaSEService.getKids().add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(), kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el petacón", null), HttpStatus.OK);
    }

    @GetMapping(path = "/invert_list")
    public ResponseEntity<ResponseDTO> invert() {
        if (listaSEService.getKids() != null) {
            listaSEService.invert();
            return new ResponseEntity<>(new ResponseDTO(200, "La lista se ha invertido", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null),
                    HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/kids_by_depart")
    public ResponseEntity<ResponseDTO> getKidsByDeptCode() {
        List<KidsByLocationDTO> kidsByLocationDTOList1 = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listaSEService.getKids().getCountKidsByDeptCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList1.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList1,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report = new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listaSEService.getKids().getReportKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(200,report, null), HttpStatus.OK);
    }

    /*
    @GetMapping(path = "/send_kid_end/{code}")
    public ResponseEntity<ResponseDTO> sendKidFinalByLetter(@PathVariable char code){  // <-------Este metodo, no me funciona
        if (listaSEService.getKids() != null) {
            listaSEService.getKids().sendKidFinalByLetter(code);
            return new ResponseEntity<>(new ResponseDTO(200,"La lista se ha organizado",null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409, "No existen niños, por lo tanto no se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/orderbystore")
    public ResponseEntity<ResponseDTO> orderKidsToStart(){
        listaSEService.getKids().orderKidsToStart();
        return new ResponseEntity<>(new ResponseDTO(200,"La lista se ha ordenado con los niños al comienzo",null), HttpStatus.OK);
    }
*/

}//fin c_Controller