package com.mobdeve.s13.group5.grubrev

import org.osmdroid.util.GeoPoint

data class CustomMarker (
    val name: String,
    val location: GeoPoint,
    val avgRating: Double) {
}