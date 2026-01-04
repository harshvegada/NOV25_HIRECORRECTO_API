package services;

import base.ScreeningControl;
import entity.common.CopyPasteCorrelations;
import entity.common.FocusAnalysis;
import entity.common.GlobalEventAnalysis;
import entity.common.PasteAnalysis;
import entity.common.QualityAnalysis;
import entity.common.TypingAnalysis;
import entity.subjectivePOJO.SubjectiveRootPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

/**
 * Service for handling subjective answer submissions
 */
public class SubjectiveService extends BaseService {

    private static final int DEFAULT_TIME_SPENT = 60;
    private static final int DEFAULT_DURATION = 54979;

    @Step("Submit subjective answer for question ID: {questionId}")
    public Response submitSubjectiveAnswer(String questionId, String experience, String candidateAnswer) {
        SubjectiveRootPayload payload = SubjectiveRootPayload.builder()
                .questionId(questionId)
                .type("subjective")
                .skill("api")
                .jobRoleId(ScreeningControl.jobRoleID)
                .experience(experience)
                .jobApplicationId(ScreeningControl.jobApplicationID)
                .timeSpent(DEFAULT_TIME_SPENT)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(candidateAnswer)
                .typingAnalysis(buildTypingAnalysis())
                .hasTypingAnalysis(true)
                .build();

        String jsonPayload = JavaToJSON.convertToJSON(payload);
        return updateCandidateResult(ScreeningControl.candidateScreeningId, jsonPayload);
    }

    @Step("Build typing analysis for subjective answer")
    private TypingAnalysis buildTypingAnalysis() {
        return TypingAnalysis.builder()
                .totalDuration(DEFAULT_DURATION)
                .totalCharacters(103)
                .keystrokeCount(119)
                .pasteEventCount(0)
                .copyEventCount(0)
                .focusLossCount(2)
                .pasteAnalysis(PasteAnalysis.createDefault())
                .typingAnalysis(TypingAnalysis.createInnerTypingAnalysis(1.87, 119, 2))
                .focusAnalysis(FocusAnalysis.builder()
                        .totalFocusEvents(4)
                        .focusLossCount(2)
                        .focusChangeFrequency(0.5)
                        .riskLevel("low")
                        .build())
                .qualityAnalysis(QualityAnalysis.builder()
                        .wordCount(15)
                        .averageWordsPerMinute(16.37)
                        .hasProperStructure(false)
                        .hasVariedVocabulary(true)
                        .qualityScore(1)
                        .riskLevel("low")
                        .build())
                .globalEventAnalysis(GlobalEventAnalysis.createDefault())
                .copyPasteCorrelations(CopyPasteCorrelations.createDefault())
                .riskScore(0)
                .privacyCompliant(true)
                .build();
    }

}
