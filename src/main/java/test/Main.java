package test;

import com.google.gson.Gson;
import model.Writer;

public class Main {
    public static void main(String[] args) {
        Writer writer = new Writer("Artem", "Nikolaev");
        System.out.println(writer);
        Writer writer1 = new Gson().fromJson(writer.toString(), Writer.class);
        System.out.println(writer1.toString());
    }
}
