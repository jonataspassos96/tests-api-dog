package dogapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo para respostas de erro da Dog API
 */
public class ErrorResponse extends BaseResponse {
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("code")
    private Integer code;
    
    // Construtores
    public ErrorResponse() {}
    
    public ErrorResponse(String status, String message, Integer code) {
        super(status);
        this.message = message;
        this.code = code;
    }
    
    // Getters e Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status='" + getStatus() + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}