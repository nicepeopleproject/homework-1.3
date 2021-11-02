package repository;

import com.google.gson.Gson;
import model.Region;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JSONRegionRepositoryImpl implements RegionRepository {

    private String repositoryFileName = "region.json";

    {
        if (!Files.exists(Paths.get(this.repositoryFileName))) {
            try {
                Files.createFile(Paths.get(this.repositoryFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Region save(Region region) {
        region.setId(this.getCurrentMaxID() + 1);
        try {
            Files.write(Paths.get(this.repositoryFileName), region.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }

    @Override
    public Region update(Region region) {
        if (region.getId() == null) {
            throw new RuntimeException("Cannot update region if id is unset.");
        }
        if (region.getId() != null && getById(region.getId()) != null) {
            List<Region> regions = getAll();
            regions.stream().forEach(tmpRegion -> {
                if (region.getId() == tmpRegion.getId()) {
                    tmpRegion.setName(region.getName());
                }
            });
            saveAll(regions);
            return region;
        }
        throw new RuntimeException("Cannot find region with such: " + region.getId() + " id.");
    }

    @Override
    public Region getById(Long aLong) {
        Optional<Region> regionOptional = getAll().stream().filter(region -> region.getId() == aLong).findFirst();
        if (regionOptional.isPresent()) {
            return regionOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Region> getAll() {
        FileInputStream fIn = null;
        FileChannel fChan = null;
        ByteBuffer mBuf;
        int count;
        List<Region> regions = null;
        try {
            StringBuffer region = new StringBuffer();
            fIn = new FileInputStream(this.repositoryFileName);
            fChan = fIn.getChannel();
            mBuf = ByteBuffer.allocate(128);
            do {
                count = fChan.read(mBuf);
                if (count != -1) {
                    if (regions == null) {
                        regions = new ArrayList<>();
                    }
                    for (int i = 0; i < count; i++) {
                        region.append((char) mBuf.get(i));
                        if ((char) mBuf.get(i) == '}') {
                            regions.add(new Gson().fromJson(region.toString(), Region.class));
                            region = new StringBuffer();
                        }
                    }
                }
                mBuf.rewind();
            } while (count != -1);
        } catch (IOException io) {

        } finally {

        }
        return regions;
    }

    @Override
    public void deleteByID(Long aLong) {
        saveAll(getAll().stream().filter(region -> region.getId() != aLong).collect(Collectors.toList()));
    }

    private void saveAll(List<Region> regions) {
        if (regions == null) {
            return;
        }
        try {
            Files.write(Paths.get(this.repositoryFileName), regions.stream().map(region -> region.toString()).collect(Collectors.joining()).getBytes());
        } catch (IOException e) {
            ;
            e.printStackTrace();
        }
    }

    private long getCurrentMaxID() {
        if (getAll() == null) {
            return 0l;
        } else {
            Optional<Long> id = getAll().stream().map(region -> region.getId()).max(Long::compareTo);
            if (id.isPresent()) {
                return id.get();
            } else {
                throw new RuntimeException("Cannot define max id.");
            }
        }
    }
}
