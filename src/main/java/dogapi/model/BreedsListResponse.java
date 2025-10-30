package dogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Classe que representa a resposta do endpoint /breeds/list/all
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BreedsListResponse extends BaseResponse {
    
    @JsonProperty("message")
    private Map<String, List<String>> message;

    public BreedsListResponse() {}

    public BreedsListResponse(Map<String, List<String>> message, String status) {
        super(status);
        this.message = message;
    }

    public Map<String, List<String>> getMessage() {
        return message;
    }

    public void setMessage(Map<String, List<String>> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BreedsListResponse{" +
                "message=" + message +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}