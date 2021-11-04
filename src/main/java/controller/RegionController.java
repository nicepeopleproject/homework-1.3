package controller;

import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

import java.util.List;

public class RegionController implements GenericController<Region, Long> {
    private RegionRepository regionRepository;

    {
        regionRepository = new JSONRegionRepositoryImpl();
    }

    @Override
    public void update(Region region) {
        regionRepository.update(region);
    }

    @Override
    public void changeNameById(Long id, String newName) {
        Region region = regionRepository.getById(id);
        region.setName(newName);
        regionRepository.update(region);
    }

    @Override
    public Region getByID(Long id) {
        return regionRepository.getById(id);
    }

    @Override
    public List<Region> getAll() {
        return regionRepository.getAllRegions();
    }

    @Override
    public void deleteByID(Long id) {
        regionRepository.deleteByID(id);

    }

    @Override
    public void deleteAll() {
        for (Region region : regionRepository.getAllRegions()) {
            regionRepository.deleteByID(region.getId());
        }
    }
}
