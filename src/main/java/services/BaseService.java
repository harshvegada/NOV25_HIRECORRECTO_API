package services;

import base.APIControlActions;
import entity.common.FileGeneratorPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Base service class containing common functionality for all service classes
 */
public abstract class BaseService extends APIControlActions {

    private static final Map<String, String> JSON_HEADERS = createJsonHeaders();

    private static Map<String, String> createJsonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    /**
     * Sets standard JSON headers for API requests
     */
    @Step("Set JSON headers for API request")
    protected void setJsonHeaders() {
        setHeaders(JSON_HEADERS);
    }

    /**
     * Generates an upload URL for media files (audio/video)
     *
     * @param payload The file generator payload
     * @return Response containing fileId and upload URL
     */
    @Step("Generate upload URL for media file")
    protected Response generateUploadUrl(FileGeneratorPayload payload) {
        setJsonHeaders();
        String jsonPayload = JavaToJSON.convertToJSON(payload);
        setBody(jsonPayload);
        return executePostAPI("/api/candidateScreening/generate-upload-url");
    }

    /**
     * Uploads a file to Azure blob storage
     *
     * @param azureURL  The Azure upload URL
     * @param filePath  Path to the local file
     * @return Response from Azure
     */
    @Step("Upload file to Azure blob storage: {filePath}")
    protected Response uploadFileToAzure(String azureURL, String filePath) {
        Path path = Paths.get(filePath);
        Response response;
        try {
            resetRequestBuilder();
            setHeader("x-ms-blob-type", "BlockBlob");
            byte[] fileContent = Files.readAllBytes(path);
            setBody(fileContent);
            response = executeMultipartPutAPIWithFullUrl(azureURL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to Azure: " + e.getMessage(), e);
        }
        return response;
    }

    /**
     * Executes an update candidate result API call
     *
     * @param candidateScreeningId The candidate screening ID
     * @param payload             The JSON payload
     * @return Response from the API
     */
    @Step("Update candidate result for screening ID: {candidateScreeningId}")
    protected Response updateCandidateResult(String candidateScreeningId, String payload) {
        setJsonHeaders();
        setBody(payload);
        return executePatchAPI("/api/candidateScreening/update-candidate-result/" + candidateScreeningId);
    }

    /**
     * Executes the analyze-response-uri API call
     *
     * @param payload The JSON payload
     * @return Response from the API
     */
    @Step("Analyze response URI")
    protected Response analyzeResponseUri(String payload) {
        setJsonHeaders();
        setBody(payload);
        return executePostAPI("/api/candidateScreening/analyze-response-uri");
    }

    /**
     * Extracts file ID from upload URL response
     */
    @Step("Extract file ID from response")
    protected String extractFileId(Response response) {
        return response.jsonPath().getString("fileId");
    }

    /**
     * Extracts Azure upload URL from response
     */
    @Step("Extract Azure upload URL from response")
    protected String extractUploadUrl(Response response) {
        return response.jsonPath().getString("azureUpload.uploadUrl");
    }

    @Step("Validate response schema: {schemaPath}")
    public void validateSchema(Response response, String schemaPath) {
        verifySchemaForResponse(response, schemaPath);
    }
}

