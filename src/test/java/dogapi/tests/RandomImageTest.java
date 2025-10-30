package dogapi.tests;

import dogapi.client.DogApiClient;
import dogapi.model.RandomImageResponse;
import dogapi.util.TestConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

/**
 * Testes para o endpoint GET /breeds/image/random
 */
@Epic("Dog API Tests")
@Feature("Random Image")
public class RandomImageTest {
    
    private DogApiClient dogApiClient;
    private Pattern imageUrlPattern;
    
    @BeforeClass
    public void setUp() {
        dogApiClient = new DogApiClient();
        imageUrlPattern = Pattern.compile(TestConstants.IMAGE_URL_PATTERN);
    }
    
    @Test(description = "Deve retornar uma imagem aleatória com sucesso")
    @Story("Buscar imagem aleatória")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Testa se o endpoint /breeds/image/random retorna uma imagem aleatória")
    public void testGetRandomImageSuccess() {
        Response response = dogApiClient.getRandomImage();
        
        // Validações básicas
        assertEquals(response.getStatusCode(), TestConstants.HTTP_OK, 
                "Status code deve ser 200");
        assertTrue(response.getContentType().contains(TestConstants.CONTENT_TYPE_JSON), 
                "Content-Type deve ser application/json");
        
        // Validações da estrutura da resposta
        RandomImageResponse imageResponse = response.as(RandomImageResponse.class);
        assertNotNull(imageResponse, "Resposta não deve ser nula");
        assertEquals(imageResponse.getStatus(), TestConstants.SUCCESS_STATUS, 
                "Status deve ser 'success'");
        assertNotNull(imageResponse.getMessage(), "Message não deve ser nulo");
        assertFalse(imageResponse.getMessage().trim().isEmpty(), 
                "URL da imagem não deve estar vazia");
    }
    
    @Test(description = "Deve validar formato da URL da imagem")
    @Story("Validar URL da imagem")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se a URL da imagem segue o padrão esperado")
    public void testRandomImageUrlFormat() {
        RandomImageResponse response = dogApiClient.getRandomImageAsObject();
        String imageUrl = response.getMessage();
        
        // Validações do formato da URL
        assertNotNull(imageUrl, "URL da imagem não deve ser nula");
        assertFalse(imageUrl.trim().isEmpty(), "URL da imagem não deve estar vazia");
        assertTrue(imageUrlPattern.matcher(imageUrl).matches(), 
                String.format("URL '%s' deve seguir o padrão esperado", imageUrl));
        assertTrue(imageUrl.startsWith("https://"), 
                "URL deve usar HTTPS");
        assertTrue(imageUrl.contains("images.dog.ceo"), 
                "URL deve ser do domínio images.dog.ceo");
        
        // Valida extensão da imagem
        assertTrue(imageUrl.matches(".*\\.(jpg|jpeg|png|gif)$"), 
                "URL deve terminar com extensão de imagem válida");
    }
    
    @Test(description = "Deve retornar imagens diferentes em chamadas consecutivas")
    @Story("Validar aleatoriedade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Testa se múltiplas chamadas retornam imagens diferentes (aleatoriedade)")
    public void testRandomnessOfImages() {
        Set<String> imageUrls = new HashSet<>();
        int numberOfCalls = 5;
        
        // Faz múltiplas chamadas
        for (int i = 0; i < numberOfCalls; i++) {
            RandomImageResponse response = dogApiClient.getRandomImageAsObject();
            imageUrls.add(response.getMessage());
        }
        
        // Verifica se há pelo menos algumas imagens diferentes
        // (É possível, mas improvável, que todas sejam iguais)
        assertTrue(imageUrls.size() > 1, 
                String.format("De %d chamadas, pelo menos 2 devem retornar imagens diferentes. " +
                        "URLs obtidas: %s", numberOfCalls, imageUrls));
    }
    
    @Test(description = "Deve validar tempo de resposta")
    @Story("Performance")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se o tempo de resposta está dentro do limite")
    public void testResponseTime() {
        Response response = dogApiClient.getRandomImage();
        
        long responseTime = response.getTime();
        assertTrue(responseTime < TestConstants.DEFAULT_TIMEOUT, 
                String.format("Tempo de resposta (%d ms) deve ser menor que %d ms", 
                        responseTime, TestConstants.DEFAULT_TIMEOUT));
    }
    
    @Test(description = "Deve validar consistência da estrutura da resposta")
    @Story("Validar estrutura da resposta")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se a estrutura da resposta é consistente em múltiplas chamadas")
    public void testResponseStructureConsistency() {
        int numberOfCalls = 3;
        
        for (int i = 0; i < numberOfCalls; i++) {
            RandomImageResponse response = dogApiClient.getRandomImageAsObject();
            
            // Validações de estrutura que devem ser consistentes
            assertNotNull(response, String.format("Resposta %d não deve ser nula", i + 1));
            assertNotNull(response.getStatus(), String.format("Status da resposta %d não deve ser nulo", i + 1));
            assertNotNull(response.getMessage(), String.format("Message da resposta %d não deve ser nulo", i + 1));
            assertEquals(response.getStatus(), TestConstants.SUCCESS_STATUS, 
                    String.format("Status da resposta %d deve ser 'success'", i + 1));
            assertTrue(response.getMessage().startsWith("https://"), 
                    String.format("URL da resposta %d deve começar com https://", i + 1));
        }
    }
    
    @Test(description = "Deve validar headers da resposta")
    @Story("Validar headers")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se os headers necessários estão presentes")
    public void testResponseHeaders() {
        Response response = dogApiClient.getRandomImage();
        
        // Validações de headers importantes
        assertNotNull(response.getHeader("Content-Type"), 
                "Header Content-Type deve estar presente");
        assertNotNull(response.getHeader("Date"), 
                "Header Date deve estar presente");
        
        // Valida se é JSON
        assertTrue(response.getContentType().contains("application/json"), 
                "Content-Type deve ser application/json");
    }
    
    @Test(description = "Deve funcionar com múltiplas chamadas simultâneas")
    @Story("Teste de carga leve")
    @Severity(SeverityLevel.MINOR)
    @Description("Testa se a API suporta múltiplas chamadas rápidas")
    public void testMultipleQuickCalls() {
        int numberOfCalls = 10;
        int successfulCalls = 0;
        
        for (int i = 0; i < numberOfCalls; i++) {
            try {
                Response response = dogApiClient.getRandomImage();
                if (response.getStatusCode() == TestConstants.HTTP_OK) {
                    successfulCalls++;
                }
            } catch (Exception e) {
                // Log mas não falha o teste - pode haver limitações de rate limiting
                System.out.println("Erro na chamada " + (i + 1) + ": " + e.getMessage());
            }
        }
        
        // Pelo menos 80% das chamadas devem ser bem-sucedidas
        double successRate = (double) successfulCalls / numberOfCalls;
        assertTrue(successRate >= 0.8, 
                String.format("Taxa de sucesso deve ser pelo menos 80%%. Obtido: %.2f%%", 
                        successRate * 100));
    }
}