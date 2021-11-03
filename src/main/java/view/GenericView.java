package view;

public interface GenericView<T, ID> {
    void showAll();

    default void chooseAction(int actionNumber) {
        switch (actionNumber) {
            case 1: // show all
                showAll();
                break;
        }
    }
}
