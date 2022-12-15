package com.example.nycschooldb.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NycSchoolEntity::class], version = 1)
abstract class NYCSDatabase: RoomDatabase() {
    abstract fun nycDao(): NycDao

    companion object{
        //singleton prevents multiple instances of data
        private var INSTANCE: NYCSDatabase? = null

        fun newInstance(context: Context): NYCSDatabase =
            INSTANCE?: synchronized(this){
                var temp = INSTANCE
                if (temp != null) return temp

                temp = Room.databaseBuilder(
                    context, NYCSDatabase::class.java, "room_nyc_db"
                ).build()
                INSTANCE = temp
                temp

            }
    }

}