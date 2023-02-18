package com.udacity.asteroidradar.domainlayer.databaselayer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

//    @Query("select * from databasevideo")
//    @Query("SELECT * FROM asteroidDatabaseModel WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
//

   @Query("select * from asteroidDatabaseModel where closeApproachDate>=:startDate and  closeApproachDate <= :endDate order by closeApproachDate asc ")
    fun getAsteroidsToNearestDate(startDate: String, endDate: String): LiveData<List<Asteroid>>

    // here will return list of asteroid list
    @Query("SELECT * FROM asteroidDatabaseModel")
    fun getAllAsteroidsList(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroids: AsteroidDatabaseModel)

}

@Database(entities = [AsteroidDatabaseModel::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AsteroidDatabase

        fun getDatabase(context: Context): AsteroidDatabase {
            synchronized(AsteroidDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid_data_base"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}