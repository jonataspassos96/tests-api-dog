package dogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que representa a resposta do endpoint /breed/{breed}/images
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BreedImagesResponse extends BaseResponse {
    
    @JsonProperty("message")
    private List<String> message;

    public BreedImagesResponse() {}

    public BreedImagesResponse(List<String> message, String status) {
        super(status);
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BreedImagesResponse{" +
                "message=" + message +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}