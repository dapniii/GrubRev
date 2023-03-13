package com.mobdeve.s13.group5.grubrev

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Review> {

            val data = ArrayList<Review>()
            data.add(
                Review(
                    "SecretAgno",
                    "Sugon",
                    "This place good",
                    5
                )
            )
            data.add(
                Review(
                    "KainanKanto",
                    "Candice",
                    "This place good",
                    4
                )
            )
            data.add(
                Review(
                    "SecretAgno",
                    "Boffa",
                    "This place good",
                    3
                )
            )
            data.add(
                Review(
                    "KuboBistro",
                    "Sugma",
                    "This place good",
                    2
                )
            )
            data.add(
                Review(
                    "KainanKanto",
                    "Chad",
                    "This place good",
                    1
                )
            )
            data.add(
                Review(
                    "SecretAgno",
                    "Sugon",
                    "This place good",
                    5
                )
            )
            data.add(
                Review(
                    "KuboBistro",
                    "Chad",
                    "This place good",
                    2
                )
            )
            data.add(
                Review(
                    "KainanKanto",
                    "Sugma",
                    "This place good",
                    5
                )
            )
            data.add(
                Review(
                    "KuboBistro",
                    "Boffa",
                    "This place good",
                    4
                )
            )

            return data;
        }
    }
}