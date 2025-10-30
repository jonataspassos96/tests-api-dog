package dogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa a resposta do endpoint /breeds/image/random
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomImageResponse extends BaseResponse {
    
    @JsonProperty("message")
    private String message;

    public RandomImageResponse() {}

    public RandomImageResponse(String message, String status) {
        super(status);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RandomImageResponse{" +
                "message='" + message + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}