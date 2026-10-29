package com.example.persistancelab;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JSONNameStore extends NameStore {
    private final String fileName = "names.json";

    @Override
    public void writeNames(Context context, ArrayList<Name> values) {
        Gson gson = new Gson();
        String json = gson.toJson(values);

        try (FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(json.getBytes());
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
            StringBuilder stringBuilder = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n'); // readLine() discards terminator
                    line = reader.readLine();
                }
                String json = stringBuilder.toString();
                Gson gson = new Gson();
                TypeToken<ArrayList<Name>> alType = new TypeToken<ArrayList<Name>>(){};
                values = gson.fromJson(json, alType);
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
