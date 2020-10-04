package com.example.a3dstep.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Excercise.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExcerciseDao excerciseDao();
}