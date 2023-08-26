package kz.btokmyrza.calorytracker.tracker_data.network.model

import com.squareup.moshi.Json

class FoodSearchResponse(
    @field:Json(name = "products")
    val products: List<ProductResponse>? = null,
) {

    class ProductResponse(
        @field:Json(name = "image_front_thumb_url")
        val imageFrontThumbUrl: String? = null,
        @field:Json(name = "nutriments")
        val nutriments: NutrimentsResponse? = null,
        @field:Json(name = "product_name")
        val productName: String? = null,
    )

    class NutrimentsResponse(
        @field:Json(name = "carbohydrates_100g")
        val carbohydrates100g: Double? = null,
        @field:Json(name = "energy-kcal_100g")
        val energyKcal100g: Double? = null,
        @field:Json(name = "fat_100g")
        val fat100g: Double? = null,
        @field:Json(name = "proteins_100g")
        val proteins100g: Double? = null,
    )
}