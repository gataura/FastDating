package com.dating.foryou.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Query("SELECT COUNT(*) FROM employee")
    int getSize();

    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getById(long id);

    @Query("SELECT * FROM employee WHERE email = :email")
    Employee getByEmail(String email);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

}
