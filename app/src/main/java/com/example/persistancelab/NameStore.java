package com.example.persistancelab;

import android.content.Context;

import java.util.ArrayList;

public abstract class NameStore {
    public abstract void writeNames(Context context, ArrayList<Name> values);

    public abstract ArrayList<Name> getNames(Context context);
}