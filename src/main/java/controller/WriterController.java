package controller;

import model.Post;
import model.Region;
import model.Writer;
import repository.JSONWriterRepositoryImpl;
import repository.WriterRepository;

import java.util.List;

public class WriterController {
    private WriterRepository writerRepository = new JSONWriterRepositoryImpl();

    public Writer createWriter(String name, String surname, Region region) {
        return writerRepository.save(new Writer(name, surname, region));
    }

    public Writer updateNameAndSurnameById(Long id, String newName, String newSurname, Region newRegion) {
        Writer writer = new Writer(newName, newSurname, newRegion);
        writer.setId(id);
        return writerRepository.update(writer);
    }

    public Writer deleteWriterById(Long id) {
        Writer writer = writerRepository.getById(id);
        writerRepository.deleteByID(id);
        return writer;
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer getWriterById(Long id) {
        return writerRepository.getById(id);
    }

    public Writer addPost(Long id, Post post) {
        Writer writer = writerRepository.getById(id);
        if (writer != null) {
            writer.addPost(post);
            return writerRepository.update(writer);
        } else {
            // or sout?
            throw new RuntimeException("Cannot find writer with such id");
        }
    }

    public Writer updatePost(Long id, Post post) {
        Writer writer = writerRepository.getById(id);
        if (writer != null) {
            writer.getPosts().forEach(p -> {
                if (p.getId().equals(post.getId())) {
                    p.setContent(post.getContent());
                }
            });
            writerRepository.update(writer);
            return writer;
        } else {
            // or sout?
            throw new RuntimeException("Cannot find writer with such id");
        }
    }
}