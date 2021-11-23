package view;

import controller.WriterController;
import model.Region;
import model.Writer;

import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private Writer currentWriter = null;
    private final RegionView regionView = new RegionView();
    private final Scanner scanner = new Scanner(System.in);

    public WriterView(WriterController writerController) {
        this.writerController = writerController;
    }

    public void setCurrentWriter(Writer currentWriter) {
        this.currentWriter = currentWriter;
    }

    public void writersMenu() {
        showWritersMenu();
        System.out.println("Current writer: ");
        if (currentWriter == null) {
            System.out.println("unset. You cannot look at post or create them.");
        } else {
            printWriter(currentWriter);
        }
        System.out.print("Choose option:");
        switch (scanner.nextInt()) {
            case 1:
                createNewWriter();
                chooseWriterById();
                writersMenu();
                break;
            case 2:
                chooseWriterById();
                writersMenu();
                break;
            case 3:
                deleteCurrentWriter();
                writersMenu();
                break;
            case 4:
                if (currentWriter != null) {
                    showAllWriterPost();
                    writersMenu();
                    break;
                }
        }
    }

    private void showAllWriterPost() {
        System.out.println("Writers posts:");
        currentWriter.getPosts().forEach(post -> System.out.printf("ID: %s Post: %s \n", post.getId(), post.getContent()));
    }

    private void deleteCurrentWriter() {
        if (currentWriter != null) {
            writerController.deleteWriterById(currentWriter.getId());
            System.out.println("Writer: ");
            printWriter(currentWriter);
            System.out.println("was successfully deleted.");
            currentWriter = null;
        } else {
            System.out.println("First you should choose current writer;");
        }
    }


    private void createNewWriter() {
        System.out.println("New writer creation.");
        System.out.print("Input writers name: ");
        String name = scanner.next();
        System.out.print("Input writers surname: ");
        String surname = scanner.next();
        Region region = regionView.chooseRegionById();
        Writer writer = writerController.createWriter(name, surname, region);
        System.out.println("New writer created: ");
        printWriter(writer);

    }

    private void printWriter(Writer writer) {
        System.out.println("Id: " + writer.getId()
                + " Name: " + writer.getFirstName()
                + " Surname: " + writer.getLastName()
                + " Region: " + writer.getRegion().getName()
                + " Number of posts: " + writer.getPosts().size());
    }

    private void showWritersMenu() {
        System.out.println("Writers menu: ");
        System.out.println("1. Create new Writer.");
        System.out.println("2. Choose existing writer by id.");
        System.out.println("3. Delete current writer.");
        if (currentWriter != null) {
            System.out.println("4. Show all posts.");
        }
        System.out.println("5. Exit.");
    }

    private void showAll() {
        writerController.getAll().forEach(this::printWriter);
    }

    public void chooseWriterById() {
        System.out.println("Choose or update writer by ID.");
        showAll();
        System.out.print("Enter writers id: ");
        Scanner scanner = new Scanner(System.in);
        Writer writer = writerController.getWriterById(scanner.nextLong());
        if (writer == null) {
            System.out.println("No writer with such id. Try one more time.");
            chooseWriterById();
        } else {
            currentWriter = writer;
        }
    }

    private void showAllWriterPosts() {
        if (currentWriter != null) {

        } else {
            System.out.println("First off all you should choose existing writer or create.");
        }
    }

    public Writer getCurrentWriter() {
        return currentWriter;
    }
}
