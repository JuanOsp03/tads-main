package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.services.ListDECircularService;
import co.edu.umanizales.tads.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/list_de_circular")
public class ListDECircularController {

    @Autowired
    private ListDECircularService listDECircularService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(200, listDECircularService.getPetsCircular().getCircular(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody @Valid PetDTO petDTO) throws ListDEException {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        Pet newPet = new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getIdentification(), petDTO.getPetType() ,petDTO.getBreed(), petDTO.getGender() , location , false);
        listDECircularService.getPetsCircular().addPetCircular(newPet);
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado a la mascota", null), HttpStatus.OK);
    }

    @PostMapping(path = "/add_to_start")
    public ResponseEntity<ResponseDTO> addPetToStart(@RequestBody @Valid PetDTO petDTO) throws ListDEException {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        Pet newPet = new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getIdentification(), petDTO.getPetType() ,petDTO.getBreed(), petDTO.getGender() , location , false);
        listDECircularService.getPetsCircular().addPetToStartCircular(newPet);
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado a la mascota", null), HttpStatus.OK);
    }

    @PostMapping(path = "/add_to_start/{position}")
    public ResponseEntity<ResponseDTO> addPetPosition(@RequestBody @Valid PetDTO petDTO, @PathVariable int position) throws ListDEException {
        if (position < 0) {
            return new ResponseEntity<>(new ResponseDTO(409, "No se puede agregar una mascota en una posicion menor a cero", null), HttpStatus.BAD_REQUEST);
        } else {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            Pet newPet = new Pet(petDTO.getName(), petDTO.getAge(), petDTO.getIdentification(), petDTO.getPetType(), petDTO.getBreed(), petDTO.getGender(), location, false);
            listDECircularService.getPetsCircular().addPosition(newPet, position);
            return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado a la mascota", null), HttpStatus.OK);
        }
    }
} // end class_ListDECircularController