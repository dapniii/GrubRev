package com.mobdeve.s13.group5.grubrev

import org.osmdroid.util.GeoPoint

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Review> {

            val data = ArrayList<Review>()
            data.add(
                Review(
                    "Secret Agno",
                    "Sugon",
                    "This place good",
                    5.0
                )
            )
            data.add(
                Review(
                    "Kainan Kanto",
                    "Candice",
                    "This place good",
                    4.5
                )
            )
            data.add(
                Review(
                    "Secret Agno",
                    "Boffa",
                    "The food was delicious and the service was excellent. Highly recommend!",
                    3.5
                )
            )
            data.add(
                Review(
                    "Kubo Bistro",
                    "Sugma",
                    "Great atmosphere and friendly staff. The food was top-notch too!",
                    2.5
                )
            )
            data.add(
                Review(
                    "Kainan Kanto",
                    "Chad",
                    "This restaurant never disappoints. The quality of the food is always amazing.",
                    1.0
                )
            )
            data.add(
                Review(
                    "Secret Agno",
                    "Sugon",
                    "A hidden gem! The food was so tasty and the prices were very reasonable.",
                    3.5
                )
            )
            data.add(
                Review(
                    "Kubo Bistro",
                    "Chad",
                    "Amazing food and the staff was very accommodating to our dietary needs.",
                    2.5
                )
            )
            data.add(
                Review(
                    "Kainan Kanto",
                    "Sugma",
                    "The flavors in every dish were perfectly balanced. Can't wait to come back!",
                    5.0
                )
            )
            data.add(
                Review(
                    "Kubo Bistro",
                    "Boffa",
                    "This place has the best burgers I've ever tasted. Highly recommend trying one!",
                    4.5
                )
            )
            data.add(
                Review(
                    "Kainan Kanto",
                    "Candice",
                    "We had a fantastic dining experience here. The ambiance was perfect and the food was divine.",
                    5.0
                )
            )
            data.add(
                Review(
                    "Kubo Bistro",
                    "Candice",
                    "Service was great and the food was out of this world. We'll definitely be back!",
                    5.0
                )
            )
            data.add(
                Review(
                    "Secret Agno",
                    "Candice",
                    "Everything about this restaurant was perfect. From the appetizers to the desserts, we were blown away.",
                    5.0
                )
            )

            return data;
        }

        fun initializeCustomMarker(): ArrayList<CustomMarker> {
            val data = ArrayList<CustomMarker>()
            data.add(
                CustomMarker(
                    "Secret Agno",
                    GeoPoint(14.56666, 120.99224),
                    5.0
                )
            )
            data.add(
                CustomMarker(
                    "Kainan Kanto",
                    GeoPoint(14.56652, 120.99257),
                    3.5
                )
            )
            data.add(
                CustomMarker(
                    "Kubo Bistro",
                    GeoPoint(14.56657, 120.99302),
                    1.3
                )
            )
            data.add(
                CustomMarker(
                    "Adobo King",
                    GeoPoint(14.56630, 120.99246),
                    0.0
                )
            )
            data.add(
                CustomMarker(
                    "Rice Inasal",
                    GeoPoint(14.56690, 120.99282),
                    1.3
                )
            )

            return data;
        }
    }
}