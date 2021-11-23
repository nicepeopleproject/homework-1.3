package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Region;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JSONRegionRepositoryImpl implements RegionRepository {

    private final String repositoryFileName = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "region.json";
    private final Type REGION_LIST_TYPE = new TypeToken<List<Region>>() {
    }.getType();

    // хотел хранить все регионы в List<Region> чтобы каждый раз не было необходимости вызывать метод getAll()
    { // Этот код можно убрать и считать требованием иметь файлы репозиториев
        if (!Files.exists(Paths.get(this.repositoryFileName))) {
            try {
                Files.createFile(Paths.get(this.repositoryFileName));
                Files.write(Paths.get(repositoryFileName), "[]".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Region save(Region region) { // create
        List<Region> regions = getAllRegionsInternal();
        region.setId(this.generateNextId(regions));
        regions.add(region);
        writeRegionsIntoFile(regions);
        return region;
    }

    @Override
    public Region update(Region region) { // change
        if (region.getId() == null) {
            throw new RuntimeException("Cannot update region if id is unset.");
        } else {
            getAllRegionsInternal().forEach(r -> {
                if (r.getId().equals(region.getId())) r.setName(region.getName());
            });
            return region;
        }
    }

    @Override
    public Region getById(Long id) {
        return getAllRegionsInternal().stream()
                .filter(region -> region.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Region> getAll() { // read
        return getAllRegionsInternal();
    }

    @Override
    public void deleteByID(Long id) { // delete
        List<Region> regions = getAllRegionsInternal();

        if (id == null) throw new RuntimeException("Cannot delete region with null id.");

        regions.removeIf(r -> r.getId().equals(id));
        writeRegionsIntoFile(regions);
    }

    private List<Region> getAllRegionsInternal() {
        List<Region> regions;
        try (JsonReader reader = new JsonReader(new FileReader(repositoryFileName))) {
            regions = new Gson().fromJson(reader, REGION_LIST_TYPE);
            return regions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void writeRegionsIntoFile(List<Region> regions) {
if (regions == null) { // Хорошая ли это реализация?
            return;
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(repositoryFileName), 32768)) {
            out.write(new Gson().toJson(regions, REGION_LIST_TYPE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long generateNextId(List<Region> regions) {
        if ((regions == null) || (regions.size() == 0)) {
            return 1L;
        } else {
            Optional<Long> id = regions.stream().map(Region::getId).max(Long::compareTo);
            return id.get() + 1;
        }
    }
}
