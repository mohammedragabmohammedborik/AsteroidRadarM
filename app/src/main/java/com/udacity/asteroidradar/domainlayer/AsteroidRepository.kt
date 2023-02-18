package com.udacity.asteroidradar.domainlayer

import android.net.Network
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NetworkConfiguration
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.service
import com.udacity.asteroidradar.dateformateutile.getCurrentDay
import com.udacity.asteroidradar.dateformateutile.getEndDate
import com.udacity.asteroidradar.domainlayer.databaselayer.AsteroidDatabase
import com.udacity.asteroidradar.domainlayer.databaselayer.AsteroidDatabaseModel
import com.udacity.asteroidradar.maperutile.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.ArrayList

class AsteroidRepository(private val database: AsteroidDatabase) {

    suspend fun getAsteroids(
        startDate: String = getCurrentDay(),
        endDate: String = getEndDate()
    ) {
        var asteroidList: ArrayList<Asteroid>
        withContext(Dispatchers.IO) {

            try {
                val  asteroidResponseBody: ResponseBody=  async {service.getAsteroidList(
                    startDate, endDate,
                    Constants.API_KEY
                ) }.await()

                asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))
                if (asteroidList==null) return@withContext
                asteroidList.forEach(){
                    database.asteroidDao.insert(it.asDataBaseModel())

                }
            }catch (ex:java.lang.Exception){}


        }
    }

    suspend fun pictureOfDay(): PictureOfDay? {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay =async { service.getPictureOfDay( Constants.API_KEY) } .await()
        }
        if (pictureOfDay.mediaType == "image") {
            return pictureOfDay
        }
        return null
    }
}