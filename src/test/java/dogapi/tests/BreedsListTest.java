package dogapi.tests;

import dogapi.client.DogApiClient;
import dogapi.model.BreedsListResponse;
import dogapi.util.TestConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Testes para o endpoint GET /breeds/list/all
 */
@Epic("Dog API Tests")
@Feature("Breeds List")
public class BreedsListTest {
    
    private DogApiClient dogApiClient;
    
    @BeforeClass
    public void setUp() {
        dogApiClient = new DogApiClient();
    }
    
    @Test(description = "Deve retornar lista de todas as raças com sucesso")
    @Story("Buscar todas as raças")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Testa se o endpoint /breeds/list/all retorna status 200 e lista de raças")
    public void testGetAllBreedsSuccess() {
        Response response = dogApiClient.getAllBreeds();
        
        // Validações do status code e headers
        assertEquals(response.getStatusCode(), TestConstants.HTTP_OK, 
                "Status code deve ser 200");
        assertTrue(response.getContentType().contains(TestConstants.CONTENT_TYPE_JSON), 
                "Content-Type deve ser application/json");
        
        // Validações da estrutura da resposta
        BreedsListResponse breedsResponse = response.as(BreedsListResponse.class);
        assertNotNull(breedsResponse, "Resposta não deve ser nula");
        assertEquals(breedsResponse.getStatus(), TestConstants.SUCCESS_STATUS, 
                "Status deve ser 'success'");
        assertNotNull(breedsResponse.getMessage(), "Message não deve ser nulo");
        assertFalse(breedsResponse.getMessage().isEmpty(), 
                "Lista de raças não deve estar vazia");
    }
    
    @Test(description = "Deve validar estrutura do JSON de resposta")
    @Story("Validar estrutura da resposta")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se a estrutura JSON está conforme esperado")
    public void testBreedsListResponseStructure() {
        BreedsListResponse response = dogApiClient.getAllBreedsAsObject();
        
        // Validações da estrutura
        assertNotNull(response.getMessage(), "Campo 'message' não deve ser nulo");
        assertNotNull(response.getStatus(), "Campo 'status' não deve ser nulo");
        assertTrue(response.isSuccess(), "Status deve indicar sucesso");
        
        // Valida se há pelo menos algumas raças conhecidas
        Map<String, List<String>> breeds = response.getMessage();
        assertTrue(breeds.containsKey("retriever"), "Deve conter a raça 'retriever'");
        assertTrue(breeds.containsKey("labrador"), "Deve conter a raça 'labrador'");
        assertTrue(breeds.containsKey("poodle"), "Deve conter a raça 'poodle'");
        
        // Verifica se a raça retriever tem a sub-raça golden
        if (breeds.containsKey("retriever")) {
            List<String> retrieverSubBreeds = breeds.get("retriever");
            assertTrue(retrieverSubBreeds.contains("golden"), "Retriever deve conter a sub-raça 'golden'");
        }
    }
    
    @Test(description = "Deve validar sub-raças quando existem")
    @Story("Validar sub-raças")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se raças que possuem sub-raças retornam a estrutura correta")
    public void testBreedsWithSubBreeds() {
        BreedsListResponse response = dogApiClient.getAllBreedsAsObject();
        Map<String, List<String>> breeds = response.getMessage();
        
        // Verifica se existe pelo menos uma raça com sub-raças
        boolean hasSubBreeds = breeds.values().stream()
                .anyMatch(subBreeds -> !subBreeds.isEmpty());
        
        assertTrue(hasSubBreeds, "Deve existir pelo menos uma raça com sub-raças");
        
        // Valida estrutura específica para retrievers (que têm sub-raças)
        if (breeds.containsKey("retriever")) {
            List<String> retrieverSubBreeds = breeds.get("retriever");
            assertNotNull(retrieverSubBreeds, "Sub-raças do retriever não devem ser nulas");
            assertFalse(retrieverSubBreeds.isEmpty(), "Retriever deve ter sub-raças");
            assertTrue(retrieverSubBreeds.contains("golden"), 
                    "Retriever deve conter a sub-raça 'golden'");
        }
    }
    
    @Test(description = "Deve validar tempo de resposta")
    @Story("Performance")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se o tempo de resposta está dentro do limite aceitável")
    public void testResponseTime() {
        Response response = dogApiClient.getAllBreeds();
        
        long responseTime = response.getTime();
        assertTrue(responseTime < TestConstants.DEFAULT_TIMEOUT, 
                String.format("Tempo de resposta (%d ms) deve ser menor que %d ms", 
                        responseTime, TestConstants.DEFAULT_TIMEOUT));
    }
    
    @Test(description = "Deve validar headers da resposta")
    @Story("Validar headers")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se os headers necessários estão presentes na resposta")
    public void testResponseHeaders() {
        Response response = dogApiClient.getAllBreeds();
        
        // Validações de headers importantes
        assertNotNull(response.getHeader("Content-Type"), 
                "Header Content-Type deve estar presente");
        assertNotNull(response.getHeader("Date"), 
                "Header Date deve estar presente");
        
        // Valida se é JSON
        assertTrue(response.getContentType().contains("application/json"), 
                "Content-Type deve ser application/json");
    }
}