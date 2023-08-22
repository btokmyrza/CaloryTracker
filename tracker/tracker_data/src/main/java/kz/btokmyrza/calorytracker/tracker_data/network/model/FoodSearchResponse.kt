package kz.btokmyrza.calorytracker.tracker_data.network.model

import com.squareup.moshi.Json

class FoodSearchResponse(
    @field:Json(name = "products")
    val products: List<ProductResponse>,
) {

    class ProductResponse(
        @field:Json(name = "image_front_thumb_url")
        val imageFrontThumbUrl: String?,
        val nutriments: NutrimentsResponse,
        @field:Json(name = "product_name")
        val productName: String?
    )

    class NutrimentsResponse(
        @field:Json(name = "carbohydrates_100g")
        val carbohydrates100g: Double,
        @field:Json(name = "energy-kcal_100g")
        val energyKcal100g: Double,
        @field:Json(name = "fat_100g")
        val fat100g: Double,
        @field:Json(name = "proteins_100g")
        val proteins100g: Double
    )
}