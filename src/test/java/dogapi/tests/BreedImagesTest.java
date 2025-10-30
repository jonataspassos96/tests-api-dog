package dogapi.tests;

import dogapi.client.DogApiClient;
import dogapi.model.BreedImagesResponse;
import dogapi.model.ErrorResponse;
import dogapi.util.TestConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * Testes para o endpoint GET /breed/{breed}/images
 */
@Epic("Dog API Tests")
@Feature("Breed Images")
public class BreedImagesTest {
    
    private DogApiClient dogApiClient;
    private Pattern imageUrlPattern;
    
    @BeforeClass
    public void setUp() {
        dogApiClient = new DogApiClient();
        imageUrlPattern = Pattern.compile(TestConstants.IMAGE_URL_PATTERN);
    }
    
    @Test(description = "Deve retornar imagens para uma raça válida")
    @Story("Buscar imagens por raça")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Testa se o endpoint /breed/{breed}/images retorna imagens para uma raça válida")
    public void testGetBreedImagesSuccess() {
        Response response = dogApiClient.getBreedImages(TestConstants.VALID_BREED);
        
        // Validações básicas
        assertEquals(response.getStatusCode(), TestConstants.HTTP_OK, 
                "Status code deve ser 200");
        assertTrue(response.getContentType().contains(TestConstants.CONTENT_TYPE_JSON), 
                "Content-Type deve ser application/json");
        
        // Validações da estrutura da resposta
        BreedImagesResponse imagesResponse = response.as(BreedImagesResponse.class);
        assertNotNull(imagesResponse, "Resposta não deve ser nula");
        assertEquals(imagesResponse.getStatus(), TestConstants.SUCCESS_STATUS, 
                "Status deve ser 'success'");
        assertNotNull(imagesResponse.getMessage(), "Message não deve ser nulo");
        assertFalse(imagesResponse.getMessage().isEmpty(), 
                "Lista de imagens não deve estar vazia");
    }
    
    @Test(description = "Deve validar URLs das imagens")
    @Story("Validar URLs das imagens")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se as URLs das imagens seguem o padrão esperado")
    public void testImageUrlsFormat() {
        BreedImagesResponse response = dogApiClient.getBreedImagesAsObject(TestConstants.VALID_BREED);
        List<String> imageUrls = response.getMessage();
        
        // Valida que há pelo menos uma imagem
        assertFalse(imageUrls.isEmpty(), "Deve retornar pelo menos uma imagem");
        
        // Valida formato de cada URL
        for (String imageUrl : imageUrls) {
            assertNotNull(imageUrl, "URL da imagem não deve ser nula");
            assertFalse(imageUrl.trim().isEmpty(), "URL da imagem não deve estar vazia");
            assertTrue(imageUrlPattern.matcher(imageUrl).matches(), 
                    String.format("URL '%s' deve seguir o padrão esperado", imageUrl));
            assertTrue(imageUrl.startsWith("https://"), 
                    "URL deve usar HTTPS");
            assertTrue(imageUrl.contains(TestConstants.VALID_BREED), 
                    String.format("URL deve conter o nome da raça '%s'", TestConstants.VALID_BREED));
        }
    }
    
    @Test(description = "Deve retornar erro para raça inválida")
    @Story("Validar erro para raça inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Testa se retorna erro apropriado para raça que não existe")
    public void testGetBreedImagesInvalidBreed() {
        Response response = dogApiClient.getBreedImages(TestConstants.INVALID_BREED);
        
        // Deve retornar erro 404
        assertEquals(response.getStatusCode(), TestConstants.HTTP_NOT_FOUND, 
                "Status code deve ser 404 para raça inválida");
        
        // Valida estrutura da resposta de erro
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse, "Resposta de erro não deve ser nula");
        assertEquals(errorResponse.getStatus(), TestConstants.ERROR_STATUS, 
                "Status deve ser 'error'");
        assertNotNull(errorResponse.getMessage(), "Mensagem de erro não deve ser nula");
    }
    
    @Test(description = "Deve validar diferentes raças conhecidas")
    @Story("Testar múltiplas raças")
    @Severity(SeverityLevel.NORMAL) 
    @Description("Testa se diferentes raças conhecidas retornam imagens")
    public void testMultipleValidBreeds() {
        String[] knownBreeds = {"labrador", "poodle", "bulldog", "beagle"};
        
        for (String breed : knownBreeds) {
            Response response = dogApiClient.getBreedImages(breed);
            
            assertEquals(response.getStatusCode(), TestConstants.HTTP_OK, 
                    String.format("Raça '%s' deve retornar status 200", breed));
            
            BreedImagesResponse imagesResponse = response.as(BreedImagesResponse.class);
            assertEquals(imagesResponse.getStatus(), TestConstants.SUCCESS_STATUS, 
                    String.format("Raça '%s' deve ter status 'success'", breed));
            assertFalse(imagesResponse.getMessage().isEmpty(), 
                    String.format("Raça '%s' deve ter pelo menos uma imagem", breed));
        }
    }
    
    @Test(description = "Deve validar tempo de resposta")
    @Story("Performance")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se o tempo de resposta está dentro do limite")
    public void testResponseTime() {
        Response response = dogApiClient.getBreedImages(TestConstants.VALID_BREED);
        
        long responseTime = response.getTime();
        assertTrue(responseTime < TestConstants.DEFAULT_TIMEOUT, 
                String.format("Tempo de resposta (%d ms) deve ser menor que %d ms", 
                        responseTime, TestConstants.DEFAULT_TIMEOUT));
    }
    
    @Test(description = "Deve validar case sensitivity")
    @Story("Validar case sensitivity")  
    @Severity(SeverityLevel.MINOR)
    @Description("Testa se o endpoint é case sensitive para nomes de raças")
    public void testBreedNameCaseSensitivity() {
        // Testa caso correto (lowercase)
        Response validResponse = dogApiClient.getBreedImages("retriever");
        assertEquals(validResponse.getStatusCode(), TestConstants.HTTP_OK, 
                "Caso correto (lowercase) deve funcionar");
        
        // Testa casos incorretos (diferentes cases) - API é case sensitive
        String[] invalidCases = {"RETRIEVER", "Retriever", "rEtRiEvEr"};
        
        for (String invalidCase : invalidCases) {
            Response response = dogApiClient.getBreedImages(invalidCase);
            
            // API é case sensitive - casos incorretos devem retornar 404
            assertEquals(response.getStatusCode(), TestConstants.HTTP_NOT_FOUND, 
                    String.format("API é case sensitive - '%s' deve retornar 404", invalidCase));
        }
    }
}