import controller.WriterController;
import view.PostView;
import view.RegionView;
import view.WriterView;

import java.util.Scanner;

public class Main {
    private final static WriterController writerController = new WriterController();
    private final static WriterView writerView = new WriterView(writerController);
    private final static Scanner scanner = new Scanner(System.in);
    private final static RegionView regionView = new RegionView();
    private final static PostView postView = new PostView(writerController, writerView);

    public static void main(String[] args) {
        System.out.println("hello you are in nice program");
        showMenu();
        System.out.print("Choose option:");
        switch (scanner.nextInt()) {
            case 1:
                writerView.writersMenu();
                main(args);
                break;
            case 2:
                regionView.regionMenu();
                main(args);
                break;
            case 3:
                if (writerView.getCurrentWriter() != null) {
                    postView.postMenu();
                    main(args);
                    break;
                }
            case 4:
                break;
        }
    }

    private static void showMenu() {
        System.out.println("Menu:");
        System.out.println("1. WriterMenu");
        System.out.println("2. Region menu.");
        if (writerView.getCurrentWriter() != null) {
            System.out.println("3. Post menu.");
        }
        System.out.println("4. Exit.");
    }
}
