package services;

import base.ScreeningControl;
import entity.common.CopyPasteAnalysis;
import entity.common.FileGeneratorPayload;
import entity.videoPOJO.VideoAnswerAnalysisPayload;
import entity.videoPOJO.VideoAnswerPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

/**
 * Service for handling video answer submissions
 */
public class VideoServices extends BaseService {

    private static final int DEFAULT_DURATION = 53958;
    private static final int DEFAULT_TIME_SPENT = 33;

    @Step("Submit video answer for question ID: {questionId}")
    public Response submitVideoAnswer(String questionId, String experience, String filePath) {
        // Generate upload URL and get file ID
        Response videoFileResponse = generateUploadUrl(FileGeneratorPayload.forVideo());
        String videoFileID = extractFileId(videoFileResponse);
        String videoFileUploadURL = extractUploadUrl(videoFileResponse);

        // Submit video answer
        String payload = buildVideoAnswerPayload(questionId, ScreeningControl.jobRoleID, experience,
                ScreeningControl.jobApplicationID, videoFileID);
        Response videoAnswerResponse = updateCandidateResult(ScreeningControl.candidateScreeningId, payload);

        // Upload video file to Azure
        uploadFileToAzure(videoFileUploadURL, filePath);

        // Analyze video response
        String question = "<p>can you tell me all the HTTP methods in details like what exactly the method will work and when to use which methods</p>";
        Response response = analyzeVideoResponse(videoFileID, question, questionId, "api", "3", experience);
        System.out.println(response.statusCode());

        return videoAnswerResponse;
    }

    @Step("Build video answer payload for question ID: {questionId}")
    private String buildVideoAnswerPayload(String questionId, String jobRoleId, String experience,
                                           String jobApplicationId, String answerVideoFileId) {
        VideoAnswerPayload payload = VideoAnswerPayload.builder()
                .questionId(questionId)
                .type("video")
                .skill("api")
                .jobRoleId(jobRoleId)
                .experience(experience)
                .jobApplicationId(jobApplicationId)
                .timeSpent(DEFAULT_TIME_SPENT)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(null)
                .copyPasteAnalysis(CopyPasteAnalysis.createDefault(DEFAULT_DURATION))
                .hasCopyPasteAnalysis(true)
                .answerVideoFileId(answerVideoFileId)
                .retakes(0)
                .build();

        return JavaToJSON.convertToJSON(payload);
    }

    @Step("Analyze video response for question ID: {questionId}")
    private Response analyzeVideoResponse(String fileId, String question, String questionId,
                                          String skillName, String maxTime, String experience) {
        VideoAnswerAnalysisPayload payload = VideoAnswerAnalysisPayload.builder()
                .fileId(fileId)
                .question(question)
                .questionId(questionId)
                .candidateScreeningId(ScreeningControl.candidateScreeningId)
                .candidateApplicationId(ScreeningControl.jobApplicationID)
                .skillName(skillName)
                .type("video")
                .jobRoleId(ScreeningControl.jobRoleID)
                .maxTime(maxTime)
                .experience(experience)
                .copyPasteAnalysis(CopyPasteAnalysis.createDefault(53779))
                .hasCopyPasteAnalysis(true)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .build();

        String jsonPayload = JavaToJSON.convertToJSON(payload);
        return analyzeResponseUri(jsonPayload);
    }

}
