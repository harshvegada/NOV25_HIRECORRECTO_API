package services;

import base.ScreeningControl;
import entity.common.CopyPasteAnalysis;
import entity.mcqPOJO.MCQRootPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

/**
 * Service for handling MCQ answer submissions
 */
public class MCQService extends BaseService {

    private static final int DEFAULT_TIME_SPENT = 9;
    private static final int DEFAULT_DURATION = 257;

    @Step("Submit MCQ answer for question ID: {questionId} with answer: {candidateAnswer}")
    public Response submitMCQAnswer(String questionId, String experience, String candidateAnswer) {
        MCQRootPayload payload = MCQRootPayload.builder()
                .questionId(questionId)
                .type("mcq")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleID)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationID)
                .timeSpent(DEFAULT_TIME_SPENT)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(candidateAnswer)
                .copyPasteAnalysis(CopyPasteAnalysis.createDefault(DEFAULT_DURATION))
                .hasCopyPasteAnalysis(true)
                .build();

        String jsonPayload = JavaToJSON.convertToJSON(payload);
        return updateCandidateResult(ScreeningControl.candidateScreeningId, jsonPayload);
    }

}
