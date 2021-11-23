package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Post;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JSONPostRepositoryImpl implements PostRepository {
    private final String repositoryFileName = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "post.json";
    private final Type POST_LIST_TYPE = new TypeToken<List<Post>>() {
    }.getType();

    {
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
    public Post save(Post post) {
        if (post == null) return null;
        List<Post> posts = getAllPostsInternal();
        post.setId(generateNextId(posts));
        posts.add(post);
        writePostsIntoFile(posts);
        return post;
    }

    @Override
    public Post update(Post post) {
        if (post.getId() == null) throw new RuntimeException("Cannot update post if id is unset.");
        else {
            getAllPostsInternal().forEach(p -> {
                if (p.getId().equals(post.getId())) {
                    if (!p.getContent().equals(post.getContent())) {
                        p.setContent(post.getContent());
                        p.setUpdated(new Date());
                        post.setUpdated(p.getUpdated());
                    }
                }
            });
        }
        return post;
    }

    @Override
    public Post getById(Long id) {
        return getAllPostsInternal().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

    @Override
    public void deleteByID(Long id) {
        if (id == null) throw new RuntimeException("Cannot delete post with null id.");

        List<Post> posts = getAllPostsInternal();
        posts.removeIf(p -> p.getId().equals(id));
        writePostsIntoFile(posts);
    }

    private List<Post> getAllPostsInternal() {
        List<Post> posts;
        try (JsonReader reader = new JsonReader(new FileReader(repositoryFileName))) {
            posts = new Gson().fromJson(reader, POST_LIST_TYPE);
            return posts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    private void writePostsIntoFile(List<Post> posts) {
        if (posts == null) {
            return;
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(repositoryFileName), 32768)) {
            out.write(new Gson().toJson(posts, POST_LIST_TYPE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long generateNextId(List<Post> posts) {
        if ((posts == null) || (posts.size() == 0)) {
            return 1l;
        } else {
            Optional<Long> id = posts.stream().map(Post::getId).max(Long::compareTo);
            return id.get() + 1;
        }
    }

}
