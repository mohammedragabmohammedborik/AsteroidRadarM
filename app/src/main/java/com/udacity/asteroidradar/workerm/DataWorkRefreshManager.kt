package com.udacity.asteroidradar.workerm

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.domainlayer.AsteroidRepository
import com.udacity.asteroidradar.domainlayer.databaselayer.AsteroidDatabase

import retrofit2.HttpException

class DataWorkRefreshManager(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.getAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}