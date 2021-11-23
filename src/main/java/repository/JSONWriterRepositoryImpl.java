package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Writer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JSONWriterRepositoryImpl implements WriterRepository {
    private final String repositoryFileName = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "writer.json";
    private final Type WRITER_LIST_TYPE = new TypeToken<List<Writer>>() {
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
    public Writer save(Writer writer) {
        if (writer == null) throw new RuntimeException("Cannot save null writer.");
        List<Writer> writers = getAllWritersInternal();
        writer.setId(generateNextId(writers));
        writers.add(writer);
        writeWritersIntoFile(writers);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        if (writer == null || writer.getId() == null)
            throw new RuntimeException("Cannot update null writer and without id");
        List<Writer> writers = getAllWritersInternal();
        writers.forEach(w -> {
            if (w.getId().equals(writer.getId())) {
                w.setFirstName(writer.getFirstName());
                w.setLastName(writer.getLastName());
                w.setRegion(writer.getRegion());
                w.setPosts(writer.getPosts());
            }
        });
        writeWritersIntoFile(writers);
        return writer;
    }

    @Override
    public Writer getById(Long id) {
        return getAllWritersInternal().stream().filter(w -> w.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    @Override
    public void deleteByID(Long id) {
        List<Writer> writers = getAllWritersInternal();
        writers.removeIf(w -> w.getId().equals(id));
        writeWritersIntoFile(writers);
    }


    private List<Writer> getAllWritersInternal() {
        List<Writer> writers;
        try (JsonReader reader = new JsonReader(new FileReader(repositoryFileName))) {
            writers = new Gson().fromJson(reader, WRITER_LIST_TYPE);
            return writers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void writeWritersIntoFile(List<Writer> writers) {
        if (writers == null) { // Хорошая ли это реализация?
            return;
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(repositoryFileName), 32768)) {
            out.write(new Gson().toJson(writers, WRITER_LIST_TYPE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long generateNextId(List<Writer> writers) {
        if ((writers == null) || (writers.size() == 0)) {
            return 1L;
        } else {
            Optional<Long> id = writers.stream().map(Writer::getId).max(Long::compareTo);
            return id.get() + 1;
        }
    }
}
