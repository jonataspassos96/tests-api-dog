package dogapi.util;

/**
 * Constantes utilizadas nos testes
 */
public class TestConstants {
    
    // Status codes esperados
    public static final int HTTP_OK = 200;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_BAD_REQUEST = 400;
    
    // Status da resposta da API
    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "error";
    
    // Raças de teste conhecidas
    public static final String VALID_BREED = "retriever";
    public static final String VALID_SUB_BREED = "golden"; // sub-raça válida do retriever
    public static final String INVALID_BREED = "invalidbreed123";
    
    // Headers esperados
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    // Timeouts
    public static final int DEFAULT_TIMEOUT = 10000; // 10 segundos
    
    // Regex patterns para validação
    public static final String IMAGE_URL_PATTERN = "^https://images\\.dog\\.ceo/breeds/.*\\.(jpg|jpeg|png|gif)$";
    
    private TestConstants() {
        // Classe utilitária - construtor privado
    }
}