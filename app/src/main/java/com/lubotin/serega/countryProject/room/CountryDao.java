package com.lubotin.serega.countryProject.room;

import com.lubotin.serega.countryProject.model.Country;

import java.util.List;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@androidx.room.Dao
public abstract class CountryDao {
    @Query("select * from country")
    public abstract List<Country> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Country country);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Country country);

    @Transaction
    public void upsert(Country country) {
        long id = insert(country);
        if (id == -1) {
            update(country);
        }

    }

}
