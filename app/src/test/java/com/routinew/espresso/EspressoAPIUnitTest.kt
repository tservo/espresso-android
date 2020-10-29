package com.routinew.espresso

import com.routinew.espresso.data.json.EspressoPacket
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@DisplayName("Given Espresso API")
class EspressoAPIUnitTest {


    val restaurantListJSON = """
    {
      "restaurants": [
        {
          "city": "San Francisco", 
          "date_established": "August 2020", 
          "email": null, 
          "id": 7, 
          "name": "Herbal", 
          "phone_num": "(415) 896-4839", 
          "state": "CA", 
          "street": "448 Larkin St", 
          "suite": null, 
          "website": "https://www.herbalrestaurant.com/", 
          "zip_code": "94102"
        }, 
        {
          "city": "San Francisco", 
          "date_established": "Pre 2004", 
          "email": null, 
          "id": 8, 
          "name": "Chutney", 
          "phone_num": "(415) 931-5541", 
          "state": "CA", 
          "street": "511 Jones St", 
          "suite": null, 
          "website": "https://www.chutneysanfrancisco.com/", 
          "zip_code": "94102"
        }
      ], 
      "success": true
    } 
    """.trimIndent()

    val restaurantJSON = """
        {
          "restaurant": {
            "city": "San Francisco", 
            "date_established": "Pre 2004", 
            "email": null, 
            "id": 2, 
            "name": "Chutney", 
            "phone_num": "(415) 931-5541", 
            "state": "CA", 
            "street": "511 Jones St", 
            "suite": null, 
            "website": "https://www.chutneysanfrancisco.com/", 
            "zip_code": "94102"
          }, 
          "success": true
        }
    """.trimIndent()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @DisplayName("When receiving /restaurants JSON")
    @Nested
    inner class RestaurantsList {
        private val espressoPacketAdapter = moshi.adapter(EspressoPacket::class.java)
        @BeforeEach
        fun whenCondition() {

        }

        @Test
        @DisplayName("Then should get a list of restaurants")
        fun thenCondition() {
            val espressoPacket = espressoPacketAdapter.fromJson(restaurantListJSON)
            assertThat("Return success packet",espressoPacket,isA(EspressoPacket::class.java))
            val restaurantList = espressoPacket!!.restaurants
            assertThat("Return list of 2 restaurants",restaurantList, hasSize(2))
            assertThat("Restaurant 1 is named 'Herbal'",
                restaurantList.get(0).name,equalTo("Herbal"))
            assertThat("Restaurant 2 is named 'Chutney'",
                restaurantList.get(1).name,equalTo("Chutney"))


        }
    }

    @DisplayName("When receiving /restaurant/{id} JSON")
    @Nested
    inner class Restaurant {
        private val espressoPacketAdapter = moshi.adapter(EspressoPacket::class.java)
        @BeforeEach
        fun whenCondition() {

        }

        @Test
        @DisplayName("Then should get a list of restaurants")
        fun thenCondition() {
            val espressoPacket = espressoPacketAdapter.fromJson(restaurantListJSON)
            assertThat("Return success packet",espressoPacket,isA(EspressoPacket::class.java))
            val restaurantList = espressoPacket!!.restaurants
            assertThat("Return list of 2 restaurants",restaurantList, hasSize(2))
            assertThat("Restaurant 1 is named 'Herbal'",
                restaurantList.get(0).name,equalTo("Herbal"))
            assertThat("Restaurant 2 is named 'Chutney'",
                restaurantList.get(1).name,equalTo("Chutney"))


        }
    }
}