package com.example.nycschooldb.model.local

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NycDao {
    @Update(entity = NycSchoolEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(nycSchoolEntity: NycSchoolEntity)

    @Query(value = "SELECT * FROM SCHOOL_LIST")
    suspend fun getListSchools(): List<NycSchoolEntity>
}