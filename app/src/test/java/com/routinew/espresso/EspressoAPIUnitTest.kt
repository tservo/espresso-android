package com.routinew.espresso

import com.routinew.espresso.data.json.EspressoRestaurantPacket
import com.routinew.espresso.data.json.EspressoRestaurantListPacket
import com.routinew.espresso.objects.Restaurant
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

    val createdRestaurantJSON = """
        {
          "name": "Kraving Kebab Pizza",
          "street": "999 Hackensack St",
          "city": "Wood-Ridge",
          "state": "NJ",
          "phone_num": "201-555-7777",
          "website": "www.chinois-nj.com",
          "email": "chinois-nj@gmail.com",
          "creator": "barista-test-user@outlook.com"
        }
    """.trimIndent()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @DisplayName("When receiving /restaurants JSON")
    @Nested
    inner class WhenRestaurantsList {
        private val espressoPacketAdapter = moshi.adapter(EspressoRestaurantListPacket::class.java)
        @BeforeEach
        fun whenCondition() {

        }

        @Test
        @DisplayName("Then should get a list of restaurants")
        fun thenCondition() {
            val espressoPacket = espressoPacketAdapter.fromJson(restaurantListJSON)
            assertThat("Return success packet",espressoPacket,isA(EspressoRestaurantListPacket::class.java))
            val restaurantList = espressoPacket!!.restaurants
            assertThat("Return list of 2 restaurants",restaurantList, hasSize(2))
            assertThat("Restaurant 1 is named 'Herbal'",
                restaurantList!![0].name,equalTo("Herbal"))
            assertThat("Restaurant 2 is named 'Chutney'",
                restaurantList[1].name,equalTo("Chutney"))


        }
    }

    @DisplayName("When receiving /restaurant/{id} JSON")
    @Nested
    inner class WhenSingleRestaurant {
        private val espressoPacketAdapter = moshi.adapter(EspressoRestaurantPacket::class.java)
        @BeforeEach
        fun whenCondition() {

        }

        @Test
        @DisplayName("Then should get a single restaurant")
        fun thenCondition() {
            val espressoPacket = espressoPacketAdapter.fromJson(restaurantJSON)
            assertThat("Return success packet",espressoPacket,isA(EspressoRestaurantPacket::class.java))
            val restaurant = espressoPacket!!.restaurant!!
            assertThat("Return a restaurant",restaurant, isA(Restaurant::class.java))

        }
    }

    @DisplayName("When Creating a restaurant")
    @Nested
    inner class WhenCreatingRestaurant {
        private val espressoPacketAdapter = moshi.adapter(Restaurant::class.java)
        

        @BeforeEach
        fun whenCondition() {

        }

        @Test
        @DisplayName("Then should get a single restaurant")
        fun thenCondition() {
            val restaurant = espressoPacketAdapter.fromJson(createdRestaurantJSON)
            assertThat("Reads a Restaurant", restaurant, isA(Restaurant::class.java))
        }

    }

}