package test;

import com.google.gson.Gson;
import model.Region;
import repository.JSONRegionRepositoryImpl;

import java.util.List;

public class RegionTest {
    public static void main(String[] args) {
        Region region = new Region("Moscow");
        JSONRegionRepositoryImpl jsonRegionRepository = new JSONRegionRepositoryImpl();
//        jsonRegionRepository.save(region);
        Region newRegion = new Region("Riga");
        newRegion.setId(3l);
        jsonRegionRepository.update(newRegion);
        List<Region> regions = jsonRegionRepository.getAll();
        regions.stream().forEach(System.out::println);
    }
}
