package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.dateformateutile.getCurrentDay
import com.udacity.asteroidradar.dateformateutile.getEndDate
import com.udacity.asteroidradar.domainlayer.AsteroidRepository
import com.udacity.asteroidradar.domainlayer.databaselayer.AsteroidDatabase.Companion.getDatabase
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel  (application: Application) : AndroidViewModel(application){

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)
    init {
        viewModelScope.launch {

           try {
               asteroidRepository.getAsteroids()
               getPictureOfDay()
           }catch (ex:Exception){}


        }
    }

    val   asteroidsList=   database.asteroidDao.getAsteroidsToNearestDate(getCurrentDay(),
        getEndDate()
    )


    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay


    private suspend fun getPictureOfDay() {

        _pictureOfDay.value = asteroidRepository.pictureOfDay()
    }



}