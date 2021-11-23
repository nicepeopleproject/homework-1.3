package postRepositoryTests;

import repository.JSONPostRepositoryImpl;
import repository.PostRepository;

public class DeleteByIdPostTest {
    public static void main(String[] args) {
        PostRepository postRepository = new JSONPostRepositoryImpl();
        postRepository.deleteByID(1l);
    }
}
