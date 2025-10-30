package dogapi.client;

import dogapi.model.BreedImagesResponse;
import dogapi.model.BreedsListResponse;
import dogapi.model.RandomImageResponse;
import dogapi.util.AllureRestAssuredFilter;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Cliente para interação com a Dog API
 */
public class DogApiClient {

    private static final String BASE_URL = "https://dog.ceo/api";

    public DogApiClient() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = Parser.JSON;
    }

    /**
     * Configura especificação base para requisições
     */
    private RequestSpecification getRequestSpecification() {
        return given()
                .contentType("application/json")
                .filter(new AllureRestAssuredFilter())
                .log().all();
    }

    /**
     * Busca lista de todas as raças
     * GET /breeds/list/all
     */
    @Step("🐕 Buscar todas as raças disponíveis")
    public Response getAllBreeds() {
        return getRequestSpecification()
                .when()
                .get("/breeds/list/all")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Busca lista de todas as raças e converte para objeto
     */
    public BreedsListResponse getAllBreedsAsObject() {
        return getAllBreeds().as(BreedsListResponse.class);
    }

    /**
     * Busca imagens de uma raça específica
     * GET /breed/{breed}/images
     */
    @Step("🖼️ Buscar imagens da raça: {breed}")
    public Response getBreedImages(String breed) {
        return getRequestSpecification()
                .pathParam("breed", breed)
                .when()
                .get("/breed/{breed}/images")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Busca imagens de uma raça específica e converte para objeto
     */
    public BreedImagesResponse getBreedImagesAsObject(String breed) {
        return getBreedImages(breed).as(BreedImagesResponse.class);
    }

    /**
     * Busca uma imagem aleatória
     * GET /breeds/image/random
     */
    @Step("🎲 Buscar imagem aleatória de cachorro")
    public Response getRandomImage() {
        return getRequestSpecification()
                .when()
                .get("/breeds/image/random")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Busca uma imagem aleatória e converte para objeto
     */
    public RandomImageResponse getRandomImageAsObject() {
        return getRandomImage().as(RandomImageResponse.class);
    }

    /**
     * Busca imagens de uma sub-raça específica
     * GET /breed/{breed}/{subbreed}/images
     */
    public Response getSubBreedImages(String breed, String subBreed) {
        return getRequestSpecification()
                .pathParam("breed", breed)
                .pathParam("subbreed", subBreed)
                .when()
                .get("/breed/{breed}/{subbreed}/images")
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Busca múltiplas imagens aleatórias
     * GET /breeds/image/random/{count}
     */
    public Response getRandomImages(int count) {
        return getRequestSpecification()
                .pathParam("count", count)
                .when()
                .get("/breeds/image/random/{count}")
                .then()
                .log().all()
                .extract()
                .response();
    }
}