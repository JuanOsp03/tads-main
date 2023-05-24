package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.services.ListaSEService;
import co.edu.umanizales.tads.services.LocationService;
import co.edu.umanizales.tads.services.RangeService;
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

    @Autowired
    private RangeService rangeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
            return new ResponseEntity<>(new ResponseDTO(200,listaSEService.getKids().getHead(),null), HttpStatus.OK);
    }

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
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado correctamente el niño", null), HttpStatus.OK);
    }

    @PostMapping(path="/add_position/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@PathVariable int position, @RequestBody Kid kid) throws ListSEException {
        try {
            listaSEService.getKids().addKidsByPosition(kid, position);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño fue añadido", null), HttpStatus.OK);
        } catch (Exception e) {
            throw new ListSEException("No se puede realizar la operación");
        }
    }

    @GetMapping(path = "/invert_list")
    public ResponseEntity<ResponseDTO> invert() throws ListSEException {
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.invert();
                return new ResponseEntity<>(new ResponseDTO(200, "La lista se ha invertido", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysToStart() throws ListSEException {
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().orderBoysToStart();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha ordenado la lista con los niños al comienzo", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/alternatekids")
    public ResponseEntity<ResponseDTO> generateAlternateKids() throws ListSEException {
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().alternateKids();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha alternado la lista", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---- sustentacion ---
    @GetMapping(path = "/alternateTwoBoysTwoGirls")
    public ResponseEntity<ResponseDTO> generateAlternateTwoBoysAndTwoGirls() throws ListSEException {
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().alternateTwoBoysAndTwoGirls();
                return new ResponseEntity<>(new ResponseDTO(200, "Se ha alternado la lista", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//--- fin sustentacion ---
    @GetMapping(path = "/deletekidbyage/{age}")
    public ResponseEntity<ResponseDTO> deleteKidByAge(@PathVariable byte age) throws ListSEException{
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().deleteKidByAge(age);
                return new ResponseEntity<>(new ResponseDTO(200, "Niños por la edad dada eliminados", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/average_kids_by_age")
    public ResponseEntity<ResponseDTO> getAverageAge() throws ListSEException{
        try {
            if (listaSEService.getKids() != null) {
                double averageAge = listaSEService.getKids().getAverageAge();
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El promedio de la edad es aproximadamente: " + averageAge, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getReportKidsByLocation() throws ListSEException{
        try {
            List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();

            if (listaSEService.getKids() != null) {
                for (Location loc : locationService.getLocations()) {
                    int count = listaSEService.getKids().getReportKidsByDeptCode(loc.getCode());
                    if (count > 0) {
                        kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                    }
                }
                return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList, null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) throws ListSEException{
        try {
            if (listaSEService.getKids() != null) {
                ReportKidsLocationGenderDTO report = new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
                listaSEService.getKids().getReportKidsByLocationGendersByAge(age, report);
                return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/winpositionkid/{ide}/{win}")
    public ResponseEntity<ResponseDTO> winPositionKid(@PathVariable String ide, @PathVariable int win) throws ListSEException{
        try {
                listaSEService.getKids().winPositionKid(ide, win, listaSEService.getKids());
                return new ResponseEntity<>(new ResponseDTO(200, "El niño se ha movido con éxito", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/losepositionkid/{id}/{lostPosition}")
    public ResponseEntity<ResponseDTO> losePositionKid(@PathVariable String id, @PathVariable int lostPosition) throws ListSEException{
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().lostPositionKid(id, lostPosition, listaSEService.getKids());
                return new ResponseEntity<>(new ResponseDTO(200, "El niño se ha movido con éxito", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @GetMapping(path = "/reportkidsbyrange_age")
    public ResponseEntity<ResponseDTO> getReportKidsRangeByAge() throws ListSEException{
        try {
            List<RangeDTO> kidsRangeList = new ArrayList<>();

            if (listaSEService.getKids() != null) {
                for (Ranges i : rangeService.getRanges()) {
                    int quantity = listaSEService.getKids().getReportByRangeAge(i.getFrom(), i.getTo());
                    kidsRangeList.add(new RangeDTO(i,quantity));
                }
                return new ResponseEntity<>(new ResponseDTO(200, "el rango de los niños es: " + kidsRangeList, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }

    @GetMapping(path = "/sendkidfinalbyletter/{initial}")
    public ResponseEntity<ResponseDTO> sendKidFinalByLetter(@PathVariable char initial) throws ListSEException{
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().sendKidToTheEndByLetter(initial);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "SE HAN ENVIADO LOS NIÑOS CON ESTA INICIAL AL FINAL", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtreme() throws ListSEException{
        try {
            if (listaSEService.getKids() != null) {
                listaSEService.getKids().changeExtremes();
                return new ResponseEntity<>(new ResponseDTO(200, "Se han intercambiado los extremos", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kids_by_depart")
    public ResponseEntity<ResponseDTO> getKidsByDeptCode() throws ListSEException{
        try{
            List<KidsByLocationDTO> kidsByLocationDTOList1 = new ArrayList<>();

            if (listaSEService.getKids() != null) {
                for (Location loc : locationService.getLocations()) {
                    int count = listaSEService.getKids().getReportKidsByDeptCode(loc.getCode());
                    if (count > 0) {
                        kidsByLocationDTOList1.add(new KidsByLocationDTO(loc, count));
                    }
                }
                return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList1,
                        null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}//fin c_Controller