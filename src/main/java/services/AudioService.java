package services;

import base.ScreeningControl;
import entity.audioPOJO.AudioAnswerPayload;
import entity.audioPOJO.AudioSubmitAnswerPayload;
import entity.common.CopyPasteAnalysis;
import entity.common.FileGeneratorPayload;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import utility.JavaToJSON;

/**
 * Service for handling audio answer submissions
 */
public class AudioService extends BaseService {

    private static final int DEFAULT_DURATION = 37954;
    private static final int DEFAULT_TIME_SPENT = 29;

    @Step("Submit audio answer for question ID: {questionId}")
    public Response submitAudioAnswer(String questionId, String expi, String audioFilePath) {
        Response audioFileResponse = generateUploadUrl(FileGeneratorPayload.forAudio());
        String audioFileID = extractFileId(audioFileResponse);
        String audioFileUploadURL = extractUploadUrl(audioFileResponse);

        uploadFileToAzure(audioFileUploadURL, audioFilePath);

        String audioFilePayload = buildAudioAnalysisPayload(audioFileID, questionId);
        Response analyzeResponse = analyzeResponseUri(audioFilePayload);
        Assert.assertEquals(200, analyzeResponse.statusCode(), "Expected HTTP 200 for Audio Analyze Response URI");

        String audioAnswerPayload = buildAudioSubmitPayload(questionId, expi, audioFileID);
        return updateCandidateResult(ScreeningControl.candidateScreeningId, audioAnswerPayload);
    }

    @Step("Build audio submit payload for question ID: {questionId}")
    private String buildAudioSubmitPayload(String questionId, String experience, String answerAudioFileId) {
        AudioSubmitAnswerPayload payload = AudioSubmitAnswerPayload.builder()
                .questionId(questionId)
                .type("audio")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleID)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationID)
                .timeSpent(DEFAULT_TIME_SPENT)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(null)
                .copyPasteAnalysis(CopyPasteAnalysis.createDefault(DEFAULT_DURATION))
                .hasCopyPasteAnalysis(true)
                .answerAudioFileId(answerAudioFileId)
                .retakes(0)
                .build();

        return JavaToJSON.convertToJSON(payload);
    }

    @Step("Build audio analysis payload for file ID: {fileId}")
    private String buildAudioAnalysisPayload(String fileId, String questionId) {
        AudioAnswerPayload payload = AudioAnswerPayload.builder()
                .fileId(fileId)
                .question("<p><span style=\"font-size: 16px\">so assume that you are doing a payment from your phone and it is redirected from your account but a retailer didn't get the any confirmation on their phone so in that case your from your end request has been submitted but that is not executed from the server perspective and not received from the retailer mobile so what kind of status code will return to you in your phone from the server</span></p>")
                .questionId(questionId)
                .candidateScreeningId(ScreeningControl.candidateScreeningId)
                .candidateApplicationId(ScreeningControl.jobApplicationID)
                .skillName("api")
                .type("audio")
                .jobRoleId(ScreeningControl.jobRoleID)
                .maxTime("1")
                .experience("4.0")
                .copyPasteAnalysis(CopyPasteAnalysis.createDefault(37061))
                .hasCopyPasteAnalysis(true)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .build();

        return JavaToJSON.convertToJSON(payload);
    }

}
