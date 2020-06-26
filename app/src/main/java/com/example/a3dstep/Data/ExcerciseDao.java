package com.example.a3dstep.Data;

import java.util.List;




import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;
        import androidx.room.Update;

@Dao
public interface ExcerciseDao {

    @Query("SELECT * FROM excercise")
    List<Excercise> getAll();

    @Insert
    void insert(Excercise excercise);

    @Delete
    void delete(Excercise excercise);

    @Update
    void update(Excercise excercise);

    @Query("DELETE FROM excercise")
    public void nukeTable();
}