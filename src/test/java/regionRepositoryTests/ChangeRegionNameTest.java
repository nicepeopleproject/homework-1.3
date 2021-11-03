package test.regionRepositoryTests;

import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

public class ChangeRegionNameTest {
    public static void main(String[] args) {
        RegionRepository regionRepository = new JSONRegionRepositoryImpl();
        Region region = regionRepository.getById(1l);
        region.setName("New region name.");
        regionRepository.update(region);
    }
}
