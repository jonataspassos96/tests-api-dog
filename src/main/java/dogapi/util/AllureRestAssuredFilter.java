package dogapi.util;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

/**
 * Filtro para capturar requests e responses do REST Assured no Allure
 */
public class AllureRestAssuredFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx) {

        // Captura dados do request
        String requestUrl = requestSpec.getURI();
        String requestMethod = requestSpec.getMethod();
        String requestHeaders = requestSpec.getHeaders().toString();
        String requestBody = requestSpec.getBody() != null ? requestSpec.getBody().toString() : "No body";

        // Executa a requisição
        Response response = ctx.next(requestSpec, responseSpec);

        // Captura dados do response
        int statusCode = response.getStatusCode();
        String responseHeaders = response.getHeaders().toString();
        String responseBody = response.getBody().asString();

        // Anexa no Allure
        logRequest(requestMethod, requestUrl, requestHeaders, requestBody);
        logResponse(statusCode, responseHeaders, responseBody);

        return response;
    }

    @Step("📤 Request: {method} {url}")
    private void logRequest(String method, String url, String headers, String body) {
        attachRequestDetails(method + " " + url + "\n\nHeaders:\n" + headers + "\n\nBody:\n" + body);
    }

    @Step("📥 Response: {statusCode}")
    private void logResponse(int statusCode, String headers, String body) {
        attachResponseDetails("Status: " + statusCode + "\n\nHeaders:\n" + headers + "\n\nBody:\n" + body);
    }

    @Attachment(value = "Request Details", type = "text/plain")
    private String attachRequestDetails(String details) {
        return details;
    }

    @Attachment(value = "Response Details", type = "text/plain")
    private String attachResponseDetails(String details) {
        return details;
    }

    @Attachment(value = "Response JSON", type = "application/json")
    public static String attachJson(String json) {
        return json;
    }
}