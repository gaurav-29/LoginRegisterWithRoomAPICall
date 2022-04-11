package com.example.practicaltest.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from User")
    List<User> getAllUsers();

    @Query("select * from User where email=:email and password=:password")
    User getUser(String email, String password);

    @Query("select * from User where email=:email")
    User getCurrentUser(String email);

    @Insert
    long insert(User user);  // Insert query returns long rowId. If sucessful, returns >0 else returns -1.

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
