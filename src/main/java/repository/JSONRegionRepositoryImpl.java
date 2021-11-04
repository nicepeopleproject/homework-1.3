package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Region;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JSONRegionRepositoryImpl implements RegionRepository {

    private final String repositoryFileName = "region.json";
    private final Type REGION_LIST_TYPE = new TypeToken<List<Region>>() {
    }.getType();

    // хотел хранить все регионы в List<Region> чтобы каждый раз не было необходимости вызывать метод getAll()
    { // Этот код можно убрать и считать требованием иметь файлы репозиториев
        if (!Files.exists(Paths.get(this.repositoryFileName))) {
            try {
                Files.createFile(Paths.get(this.repositoryFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Region save(Region region) { // create
        List<Region> regions = getAll();
        region.setId(this.getCurrentMaxID(regions) + 1);
        regions.add(region);
        saveAll(regions);
        return region;
    }

    @Override
    public Region update(Region region) { // change
        if (region.getId() == null) {
            throw new RuntimeException("Cannot update region if id is unset.");
        } else {
            List<Region> regions = getAll();
            Region currentRegion = getById(region.getId(), regions);
            if (currentRegion != null) {
                regions = deleteFromListByID(regions, currentRegion.getId());
                regions.add(region);
                saveAll(regions);
            } else {
                throw new RuntimeException("Cannot find region with such: " + region.getId() + " id.");
            }
            return region;
        }
    }

    @Override
    public Region getById(Long id) {
        return getById(id, getAll());
    }

    private Region getById(Long id, List<Region> regions) {
        Optional<Region> regionOptional = regions.stream().filter(region -> region.getId().equals(id)).findFirst();
        return regionOptional.orElse(null);
    }

    @Override
    public List<Region> getAll() { // read
        List<Region> regions;
        try (JsonReader reader = new JsonReader(new FileReader(repositoryFileName))) {
            regions = new Gson().fromJson(reader, REGION_LIST_TYPE);
            return regions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteByID(Long id) { // delete
//        saveAll(getAll().stream().filter(region -> region.getId() != id).collect(Collectors.toList()));
        List<Region> regions = getAll();
        if (id != null) {
            if (getById(id, regions) == null) {
                throw new RuntimeException("Region with such id doesn't exist.");
            } else {
                saveAll(deleteFromListByID(regions, id));
            }
        } else {
            throw new RuntimeException("Cannot delete region with null id.");
        }
    }

    private List<Region> deleteFromListByID(List<Region> regions, Long id) {
        if (!id.equals(regions.get(regions.size() - 1).getId())) {
            regions.set(regions.indexOf(getById(id, regions)), regions.get(regions.size() - 1));
        }
        regions.remove(regions.size() - 1);
        return regions;
    }

    private void saveAll(List<Region> regions) {
        if (regions == null) {
            return;
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(repositoryFileName), 32768)) {
            out.write(new Gson().toJson(regions, REGION_LIST_TYPE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getCurrentMaxID(List<Region> regions) {
        if ((regions == null) || (regions.size() == 0)) {
            return 0L;
        } else {
            Optional<Long> id = regions.stream().map(Region::getId).max(Long::compareTo);
            if (id.isPresent()) {
                return id.get();
            } else {
                throw new RuntimeException("Cannot define max id.");
            }
        }
    }

}
