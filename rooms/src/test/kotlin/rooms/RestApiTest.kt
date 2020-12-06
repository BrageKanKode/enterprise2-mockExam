package rooms

import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import rooms.RestApi.Companion.LATEST
import javax.annotation.PostConstruct

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [(Application::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RestApiTest{

    @LocalServerPort
    protected var port = 0

    @PostConstruct
    fun init(){
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }


//    @Test
//    fun testGetImg(){
//
//        RestAssured.given().get("/api/rooms/imgs/001-monster.svg")
//                .then()
//                .statusCode(200)
//                .contentType("image/svg+xml")
//                .header("cache-control", Matchers.`is`(Matchers.notNullValue()))
//    }

    @Test
    fun testGetCollection(){

        RestAssured.given().get("/api/room/collection_$LATEST")
                .then()
                .statusCode(200)
                .body("data.rooms.size", Matchers.greaterThan(10))
    }


    @Test
    fun testGetCollectionOldVersion(){

        RestAssured.given().get("/api/room/collection_v0_002")
                .then()
                .statusCode(200)
                .body("data.rooms.size", Matchers.greaterThan(10))
    }
}
