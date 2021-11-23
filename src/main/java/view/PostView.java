package view;

import controller.PostController;
import controller.WriterController;
import model.Post;

import java.util.Scanner;

public class PostView {
    private final PostController postController = new PostController();
    private final WriterController writerController;
    private final Scanner scanner = new Scanner(System.in);
    private final WriterView writerView;

    public PostView(WriterController writerController, WriterView writerView) {
        this.writerController = writerController;
        this.writerView = writerView;
    }

    public void postMenu() {
        showPostMenu();
        System.out.print("Choose option: ");
        switch (scanner.nextInt()) {
            case 1:
                showAll();
                postMenu();
                break;
            case 2:
                createNewPost();
                postMenu();
                break;
            case 3:
                updatePost();
                postMenu();
                break;
            case 4:
                deletePost();
                postMenu();
                break;
        }
    }

    private void updatePost() {
        System.out.println("Choose post by id: ");
        Long id = scanner.nextLong();
        Post post = postController.getById(id);
        if (!id.equals(-1l)) {
            if (post == null) {
                System.out.println("Choose post from existing one or input -1 for exit.");
            } else {
                System.out.print("Input new content: ");
                String newContent = scanner.next();
                System.out.println("Post:");
                Post p = postController.update(id, newContent);
                printPost(p);
                writerView.setCurrentWriter(writerController.updatePost(writerView.getCurrentWriter().getId(), p));
                printPost(p);
                System.out.println("was updated.");
            }
        }
    }

    private void deletePost() {
        System.out.println("Delete post by id:");
        System.out.println("All posts:");
        showAll();
        System.out.print("Enter post id: ");
        System.out.println("(or -1 for exit)");
        Long id = scanner.nextLong();
        if (!id.equals(1l)) {
            Post post = postController.getById(id);
            if (post != null) {
                postController.deletePostById(id);
                // TODO should delete post from writer post list
            } else {
                System.out.println("You should choose post from existing or exit. Try one more time.");
            }
        }
    }

    private void createNewPost() {
        System.out.println("Create new post.");
        System.out.print("Input post content:");
        String content = scanner.next();
        Post post = postController.createPost(writerView.getCurrentWriter().getId(), content);
        writerView.setCurrentWriter(writerController.addPost(writerView.getCurrentWriter().getId(), post));
        //TODO should save writer
        System.out.println("New post created!");

    }

    private void showAll() {
        System.out.println("All posts:");
        writerView.getCurrentWriter().getPosts().forEach(post -> printPost(post));
    }

    private void printPost(Post post) {
        System.out.println(post.getId() + ":" + post.getContent());
    }

    private void showPostMenu() {
        System.out.println("Post menu:");
        System.out.println("1. Show all posts.");
        System.out.println("2. Create new post.");
        System.out.println("3. Update post.");
        System.out.println("4. Delete post.");
        System.out.println("5. Exit.");
    }

}
