package services;

import base.ScreeningControl;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for fetching screening details and questions
 */
public class ScreeningDetailsServices extends BaseService {

    @Step("Get screening details for screening test ID: {screenTestID}")
    public Response getScreeningDetails(String screenTestID) {
        setHeader("Accept", "application/json");

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("candidateApplicationId", ScreeningControl.jobApplicationID);
        queryParams.put("candidateScreeningId", ScreeningControl.candidateScreeningId);
        setQueryParams(queryParams);

        return executeGetAPI("/api/candidateScreening/get-screening-questions/" + screenTestID);
    }

}
