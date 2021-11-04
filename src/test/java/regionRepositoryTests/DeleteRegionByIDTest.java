package regionRepositoryTests;

import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

public class DeleteRegionByIDTest {
    public static void main(String[] args) {
        RegionRepository regionRepository = new JSONRegionRepositoryImpl();
        regionRepository.deleteByID(1l);
    }
}
