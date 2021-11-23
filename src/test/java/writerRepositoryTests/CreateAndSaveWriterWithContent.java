package writerRepositoryTests;

import model.Post;
import model.Region;
import model.Writer;
import repository.JSONWriterRepositoryImpl;
import repository.WriterRepository;

public class CreateAndSaveWriterWithContent {
    public static void main(String[] args) {
        WriterRepository writerRepository = new JSONWriterRepositoryImpl();
        Post post = new Post(1l, "Content");
        post.setId(1l);
        Region region = new Region("Moscow");
        region.setId(2l);
        Writer writer = new Writer("Artem", "Nikolaev", region);
        writer.setId(3l);
        writer.addPost(post);
        writerRepository.save(writer);
    }
}
