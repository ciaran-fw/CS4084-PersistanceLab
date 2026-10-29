package com.example.persistancelab;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringNameStore extends NameStore {
    private final String fileName = "names.csv";

    @Override
    public void writeNames(Context context, ArrayList<Name> values) {
        StringBuilder names = new StringBuilder();

        if (!values.isEmpty()) {
            for (int i = 0; i < values.size(); i++) {
                names.append(values.get(i).getFirst()).append("|").append(values.get(i).getLast()).append("\n");
            }
        }

        try (FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(names.toString().getBytes());
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public ArrayList<Name> getNames(Context context) {
        ArrayList<Name> values = new ArrayList<>();

        try (FileInputStream fileInputStream = context.openFileInput(fileName)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();

                while (line != null) {
                    String[] name = line.split("\\|");
                    values.add(new Name(name[0], name[1]));
                    line = reader.readLine();
                }
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        catch (FileNotFoundException exception) {
            return values;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        return values;
    }
}
