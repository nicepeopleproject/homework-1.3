package view;

import controller.RegionController;
import model.Region;
import repository.JSONRegionRepositoryImpl;
import repository.RegionRepository;

import java.sql.SQLOutput;
import java.util.Scanner;

public class RegionView {
    private RegionController regionController = new RegionController();
    private Scanner scanner = new Scanner(System.in);

    public void regionMenu() {
        showRegionMenu();
        System.out.print("Choose option:");
        switch (scanner.nextInt()) {
            case 1:
                createNewRegion();
                regionMenu();
                break;
            case 2:
                updateRegion();
                regionMenu();
                break;
            case 3:
                deleteRegion();
                regionMenu();
                break;
            case 4:
                showAll();
                regionMenu();
                break;
            case 5:
                break;
        }
    }

    private void deleteRegion() {
        System.out.println("You can delete region choosing from existing ones:");
        System.out.println("(if you want to exit input -1)");
        showAll();
        System.out.print("Enter region id:");
        Long id = scanner.nextLong();
        if (!id.equals(-1l)) {
            Region region = regionController.getById(id);
            if (region == null) {
                System.out.println("You should choose id of existing region. Try one more time.");
                deleteRegion();
            } else {
                region = regionController.deleteRegion(region.getId());
                System.out.println("Region: ");
                printRegion(region);
                System.out.println("was deleted.");
            }
        }
    }

    public Region chooseRegionById() {
        System.out.println("Choose region:");
        System.out.println("or enter -1 to create new region.");
        showAll();
        System.out.println("Enter regions id:");
        Long id = scanner.nextLong();
        Region region = regionController.getById(id);
        if (id.equals(-1l)) {
            return createNewRegion();
        } else {
            if (region == null) {
                System.out.println("Region with such id doesn't exists. Try one more or create new one.");
                chooseRegionById();
                return null;
            } else {
                return region;
            }
        }
    }

    private void updateRegion() {
        System.out.println("Region updater.");
        System.out.println("If you want to update region you should enter regions id from list:");
        System.out.println("(if you want to exit input -1)");
        showAll();
        System.out.print("Enter region id: ");
        Long id = scanner.nextLong();
        Region region = regionController.getById(id);
        if (region == null) {
            System.out.println("You should choose id of existing region or enter -1 for exit. Try one more time.");
            updateRegion();
        } else {
            System.out.print("Enter new name: ");
            String newName = scanner.next();
            region.setName(newName);
            System.out.println("Region: ");
            printRegion(regionController.update(region));
            System.out.println("was updated.");
        }
    }

    private Region createNewRegion() {
        System.out.println("Create new region.");
        System.out.print("Input new region name: ");
        String newName = scanner.next();
        System.out.println("Region:");
        Region region = regionController.createRegion(newName);
        printRegion(region);
        System.out.println("was created.");
        return region;
    }

    private void showRegionMenu() {
        System.out.println("Region menu:");
        System.out.println("1. Create new region.");
        System.out.println("2. Update region.");
        System.out.println("3. Delete region.");
        System.out.println("4. Show all regions.");
        System.out.println("5. Exit.");
    }

    private void showAll() {
        regionController.getAll().forEach(region -> printRegion(region));
    }

    private void printRegion(Region region) {
        System.out.printf("ID: %s Name: %s\n", region.getId(), region.getName());
    }
}
