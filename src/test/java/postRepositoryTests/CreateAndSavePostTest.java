package postRepositoryTests;

import model.Post;
import repository.JSONPostRepositoryImpl;
import repository.PostRepository;

public class CreateAndSavePostTest {
    public static void main(String[] args) {
        PostRepository postRepository = new JSONPostRepositoryImpl();
        Post post = new Post(1l,"It's my first post. Be kind.");
        postRepository.save(post);
    }
}
