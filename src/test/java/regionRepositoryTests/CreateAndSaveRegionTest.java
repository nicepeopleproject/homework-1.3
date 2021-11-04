package regionRepositoryTests;

import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

public class CreateAndSaveRegionTest {
    public static void main(String[] args) {
        RegionRepository regionRepository = new JSONRegionRepositoryImpl();
        Region region1 = new Region("Region First");
        Region region2 = new Region("Region Second");
        regionRepository.save(region1);
        regionRepository.save(region2);

    }
}
