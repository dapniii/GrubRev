package com.mobdeve.s13.group5.grubrev

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Review> {

            val data = ArrayList<Review>()
            data.add(
                Review(
                    "Sugon",
                    "This place good",
                    5
                )
            )
            data.add(
                Review(
                    "Candice",
                    "This place good",
                    4
                )
            )
            data.add(
                Review(
                    "Boffa",
                    "This place good",
                    3
                )
            )
            data.add(
                Review(
                    "Sugma",
                    "This place good",
                    2
                )
            )
            data.add(
                Review(
                    "Chad",
                    "This place good",
                    1
                )
            )
            data.add(
                Review(
                    "Sugon",
                    "This place good",
                    5
                )
            )

            return data;
        }
    }
}