package view;

import controller.RegionController;
import model.Region;

import java.util.List;

public class RegionView implements GenericView<Region, Long> {
    private RegionController regionController;

    {
        regionController = new RegionController();
    }

    @Override
    public void showAll() {
        List<Region> regions = regionController.getAll();
        if (regions != null && regions.size() > 0) {
            for (Region region : regionController.getAll()) {
                System.out.println(region);
            }
        } else {
            System.out.println("Regions repository is empty.");
        }
    }
}
