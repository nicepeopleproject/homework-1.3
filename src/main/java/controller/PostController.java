package controller;

import model.Post;
import repository.JSONPostRepositoryImpl;
import repository.PostRepository;

import java.util.List;

public class PostController {
    private PostRepository postRepository = new JSONPostRepositoryImpl();

    public Post createPost(Long writerId, String content) {
        return postRepository.save(new Post(writerId, content));
    }

    public Post deletePostById(Long id) {
        Post post = postRepository.getById(id);
        postRepository.deleteByID(id);
        return post;
    }

    public Post changePostContentById(Long id, Long writerId, String newContent) {
        Post post = new Post(writerId, newContent);
        post.setId(id);
        return postRepository.update(post);
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post update(Long postId, String newContent) {
        Post post = postRepository.getById(postId);
        post.setContent(newContent);
        return postRepository.update(post);
    }
}
