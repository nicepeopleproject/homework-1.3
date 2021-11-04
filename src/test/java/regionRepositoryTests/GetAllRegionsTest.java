package regionRepositoryTests;

import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

public class GetAllRegionsTest {
    public static void main(String[] args) {
        RegionRepository regionRepository = new JSONRegionRepositoryImpl();
        for (Region region : regionRepository.getAll()) {
            System.out.println(region);
        }
    }
}
