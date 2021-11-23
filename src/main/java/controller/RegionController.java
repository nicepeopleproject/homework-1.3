package controller;

import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

import java.util.List;

public class RegionController {
    private RegionRepository regionRepository = new JSONRegionRepositoryImpl();

    public Region createRegion(String regionName) {
        return regionRepository.save(new Region(regionName));
    }

    public Region deleteRegion(Long id) {
        Region region = regionRepository.getById(id);
        regionRepository.deleteByID(id);
        return region;
    }

    public Region changeRegionNameById(Long id, String newName) {
        Region region = new Region(newName);
        region.setId(id);
        return regionRepository.update(region);
    }

    public Region getById(Long id) {
        return regionRepository.getById(id);
    }

    public List<Region> getAll() {
        return regionRepository.getAll();
    }

    public Region update(Region region) {
        if (regionRepository.getById(region.getId()) == null) {
            return null;
        } else {
            return regionRepository.update(region);
        }
    }
}
