package dogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe base para respostas da API que contém campos comuns
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
    
    @JsonProperty("status")
    private String status;

    public BaseResponse() {}

    public BaseResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return "success".equals(status);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}