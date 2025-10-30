package dogapi.tests;

import dogapi.client.DogApiClient;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Suite de testes integrados que valida cenários end-to-end
 */
@Epic("Dog API Tests")
@Feature("Integration Tests")
public class IntegrationTest {
    
    private DogApiClient dogApiClient;
    
    @BeforeClass
    public void setUp() {
        dogApiClient = new DogApiClient();
    }
    
    @Test(description = "Deve executar um fluxo completo: listar raças -> buscar imagens -> buscar imagem aleatória")
    @Story("Fluxo completo da API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste de integração que executa um fluxo completo de uso da API")
    public void testCompleteApiFlow() {
        // 1. Buscar lista de raças
        var breedsResponse = dogApiClient.getAllBreedsAsObject();
        assertTrue(breedsResponse.isSuccess(), "Busca de raças deve ser bem-sucedida");
        assertFalse(breedsResponse.getMessage().isEmpty(), "Deve retornar pelo menos uma raça");
        
        // 2. Pegar a primeira raça disponível
        String firstBreed = breedsResponse.getMessage().keySet().iterator().next();
        assertNotNull(firstBreed, "Deve haver pelo menos uma raça disponível");
        
        // 3. Buscar imagens dessa raça
        var breedImagesResponse = dogApiClient.getBreedImagesAsObject(firstBreed);
        assertTrue(breedImagesResponse.isSuccess(), 
                String.format("Busca de imagens para a raça '%s' deve ser bem-sucedida", firstBreed));
        assertFalse(breedImagesResponse.getMessage().isEmpty(), 
                String.format("Raça '%s' deve ter pelo menos uma imagem", firstBreed));
        
        // 4. Buscar uma imagem aleatória
        var randomImageResponse = dogApiClient.getRandomImageAsObject();
        assertTrue(randomImageResponse.isSuccess(), "Busca de imagem aleatória deve ser bem-sucedida");
        assertNotNull(randomImageResponse.getMessage(), "Deve retornar uma URL de imagem");
        assertTrue(randomImageResponse.getMessage().startsWith("https://"), 
                "URL da imagem aleatória deve ser válida");
    }
    
    @Test(description = "Deve validar consistência dos dados entre endpoints")
    @Story("Consistência de dados")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se os dados são consistentes entre diferentes endpoints")
    public void testDataConsistency() {
        // Buscar lista de raças
        var breedsResponse = dogApiClient.getAllBreedsAsObject();
        var availableBreeds = breedsResponse.getMessage().keySet();
        
        // Verificar se uma raça conhecida existe e tem imagens
        String testBreed = "golden";
        if (availableBreeds.contains(testBreed)) {
            var imagesResponse = dogApiClient.getBreedImagesAsObject(testBreed);
            assertTrue(imagesResponse.isSuccess(), 
                    String.format("Raça '%s' listada deve ter imagens disponíveis", testBreed));
            
            // Verificar se as URLs das imagens contêm o nome da raça
            for (String imageUrl : imagesResponse.getMessage()) {
                assertTrue(imageUrl.contains(testBreed), 
                        String.format("URL da imagem deve conter o nome da raça '%s': %s", testBreed, imageUrl));
            }
        }
    }
}