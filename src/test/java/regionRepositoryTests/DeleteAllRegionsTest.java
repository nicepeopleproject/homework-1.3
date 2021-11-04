package regionRepositoryTests;

import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

public class DeleteAllRegionsTest {
    public static void main(String[] args) {
        RegionRepository regionRepository = new JSONRegionRepositoryImpl();
        for (Region region : regionRepository.getAllRegions()) {
            regionRepository.deleteByID(region.getId());
        }
    }
}
