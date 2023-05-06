package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.RangeDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.services.ListDEService;
import co.edu.umanizales.tads.services.LocationService;
import co.edu.umanizales.tads.services.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/list_de")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;

    @Autowired
    private RangeService rangeService;

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(200, listDEService.getPets().printPets() , null), HttpStatus.OK);
    }

    @PostMapping(path = "/add_pet")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) throws ListDEException {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null){
                throw new ListDEException("La ubicación no existe");
            }
            Pet newPet = new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getIdentification(), petDTO.getPetType() ,petDTO.getBreed(), petDTO.getGender() , location);

            listDEService.getPets().addPet(newPet);
            return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado a la mascota", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/add_pet_to_start")
    public ResponseEntity<ResponseDTO> addPetToStart(@RequestBody Pet pet) throws ListDEException {
        try {
            listDEService.getPets().addPetToStart(pet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue añadida al inicio", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se ha producido un error al añadir la mascota al inicio", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/delete_pet_by_age/{age}")
    public ResponseEntity<ResponseDTO> deletePetByAge(@PathVariable byte age) {
        try {
            listDEService.getPets().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota fue eliminada exitosamente", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al eliminar la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/delete_pet_by_id/{id}")
    public ResponseEntity<ResponseDTO> deletePetsById(@PathVariable String id) {
        try {
            listDEService.getPets().deletePetByIdentification(id);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota fue eliminada exitosamente", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al eliminar la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/invert_pets")
    public ResponseEntity<ResponseDTO> invertPets() {
        try {
            listDEService.getPets().invertPets();
            return new ResponseEntity<>(new ResponseDTO(200, "Se ha invertido la lista", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Ocurrió un error al invertir la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/pets_to_start")
    public ResponseEntity<ResponseDTO> addPetsToStart() {
        try {
            listDEService.getPets().addPetsToStart();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al agregar el primer niño", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/alternate_pets")
    public ResponseEntity<ResponseDTO> alternatePets() {
        try {
            listDEService.getPets().alternatePets();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la accion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "La operacion tuvo exito, se han alternado las mascotas", null), HttpStatus.OK);
    }

    @GetMapping(path = "/average_pets")
    public ResponseEntity<ResponseDTO> getAveragePetsByAge() {
        try {
            double averageAge = listDEService.getPets().averagePetByAge();
            return new ResponseEntity<>(new ResponseDTO(200, "La edad promedio de mascotas es: " + averageAge, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/add_pet_by_position/{position}")
    public ResponseEntity<ResponseDTO> addPetByPosition(@RequestBody Pet pet, @PathVariable int position) {
        try {
            listDEService.getPets().addPetByPosition(pet,position);
            return new ResponseEntity<>(new ResponseDTO(200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/win_position_pet/{code}/{move}")
    public ResponseEntity<ResponseDTO> winPosition(@PathVariable String code, @PathVariable int move) {
        try {
            listDEService.getPets().winPosition(code,move);
            return new ResponseEntity<>(new ResponseDTO(200, "Accion realizada con exito, se ha podido adelantar la posicion de la mascota", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/lost_position_pet/{code}/{position}")
    public ResponseEntity<ResponseDTO> lostPositionPet(@PathVariable String code, @PathVariable int position) {
        try {
            listDEService.getPets().lostPositionPet(code,position);
            return new ResponseEntity<>(new ResponseDTO(200, "Accion realizada con exito, se ha podido perder la posicion de la mascota ", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Se produjo un error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/report_range_by_age")
    public ResponseEntity<ResponseDTO> getReportRangeByAgePets(){
        try {
            List<RangeDTO> PetsRangeList = new ArrayList<>();
            for (Ranges i : rangeService.getRanges()){
                int quantity = listDEService.getPets().getReportPetByRangeAge(i.getFrom(), i.getTo());
                PetsRangeList.add(new RangeDTO(i,quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(200,"Accion realizada con exito, el rango de los niños es: "+PetsRangeList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,"Error al obtener el rango de edades", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/report_by_city")
    public ResponseEntity<ResponseDTO> getReportPetsByLocationCode(){
        List<KidsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try{
            for(Location loc: locationService.getLocations()){
                int count = listDEService.getPets().getReportPetsByLocationCode(loc.getCode());
                if(count > 0){
                    petsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(200,petsByLocationDTOList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,"Error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/report_by_department")
    public ResponseEntity<ResponseDTO> getReportPetsByDeptCode(){
        List<KidsByLocationDTO> listPetsByLocationDTO= new ArrayList<>();
        try{
            for(Location loc: locationService.getLocations()){
                int count = listDEService.getPets().getReportPetsByDeptCode(loc.getCode());
                if(count > 0){
                    listPetsByLocationDTO.add(new KidsByLocationDTO(loc,count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(200,listPetsByLocationDTO, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,"Error al realizar la operacion", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/send_pet_final_by_letter/{letter}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char letter){
        try{
            listDEService.getPets().sendPetToTheEndByLetter(Character.toUpperCase(letter));
            return new ResponseEntity<>(new ResponseDTO(200,"Accion realizada con exito, las mascotas con la letra dada se han enviado al final de la lista", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} // end class controller