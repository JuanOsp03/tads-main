package co.edu.umanizales.tads.services;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class LocationService {
    private List<Location> locations;

    public LocationService(){
        locations = new ArrayList<>();
        locations.add(new Location("169","Colombia"));
        locations.add(new Location("16905","Antioquia"));
        locations.add(new Location("16917","Caldas"));
        locations.add(new Location("16963","Risaralda"));
        locations.add(new Location("16905001","Medellin"));
        locations.add(new Location("1693001","Pereira"));
        locations.add(new Location("16917001","Manizales"));
        locations.add(new Location("16917003","Chinchina"));
    }

    public List<Location> getLocationByCodeSize(int size){
        List<Location> locationList = new ArrayList<>();
        for (Location loc : locations){
            if (loc.getCode().length()==size){
                locationList.add(loc);
            }
        }
        return locationList;
    }
    public Location getLocationByCode(String code){

        for (Location loc : locations){
            if (loc.getCode().equals(code));{
                return loc;
            }
        }
        return null;
    }
}
