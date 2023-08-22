package kz.btokmyrza.calorytracker.tracker_data.network

import kz.btokmyrza.calorytracker.tracker_data.network.model.FoodSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val PARAM_SEARCH = "search_simple=1"
private const val PARAM_JSON = "json=1"
private const val PARAM_ACTION = "action=process"
private const val PARAM_FIELDS = "fields=product_name,nutriments,image_front_thumb_url"

interface OpenFoodAPI {

    @GET("cgi/search.pl?$PARAM_SEARCH&$PARAM_JSON&$PARAM_ACTION&$PARAM_FIELDS")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): FoodSearchResponse

    companion object {
        const val BASE_URL = "https://us.openfoodfacts.org/"
    }
}