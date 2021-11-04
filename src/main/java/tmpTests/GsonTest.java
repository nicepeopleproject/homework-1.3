package tmpTests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Region;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonTest {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        List<String> list = Arrays.asList(new String[]{"1", "2", "3"});
        String listStr = gson.toJson(list);

        List<String> backInToList = gson.fromJson(listStr, List.class);
        for (String s : backInToList) {
            System.out.println(s);
        }
        String str = "[]";
        Type type = new TypeToken<List<Region>>() {
        }.getType();
        List<Region> regions = gson.fromJson(str, type);
        System.out.println(regions);
        for (Region region : regions) {
            System.out.println(region);
        }
        System.out.println(gson.toJson(regions));
        JsonReader reader = new JsonReader(new FileReader("C:\\PersonalProjects\\homeTask1.3\\region.json"));
        System.out.println(gson.fromJson(reader,type).toString());
        JsonWriter jsonWriter = new JsonWriter(new BufferedWriter(new FileWriter("C:\\PersonalProjects\\homeTask1.3\\region.json")));
        Region region = new Region("Riga");
        region.setId(1L);
        regions.add(region);
        System.out.println( gson.toJson(regions, type));
        gson.toJson(regions, type, jsonWriter);
    }
}
