package com.udacity.asteroidradar.domainlayer.databaselayer

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity
data class AsteroidDatabaseModel
    (
    @PrimaryKey
    val id: Long, val codename: String,val closeApproachDate: String, val absoluteMagnitude: Double,
    val estimatedDiameter: Double, val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

