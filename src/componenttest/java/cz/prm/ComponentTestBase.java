package cz.prm;

import static io.restassured.RestAssured.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpStatus.OK;

import io.restassured.RestAssured;
import io.restassured.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class ComponentTestBase {

   @BeforeEach
   public void setup() {
      RestAssured.baseURI = "http://localhost/";
      RestAssured.port = 8091;
   }

   public <T> List<T> getMany(String url, TypeRef<List<T>> type) {
      return when().get(url).then().log().ifError().assertThat()
          .statusCode(OK.value())
          .extract()
          .as(type);
   }

}
