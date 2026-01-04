package testScripts;

import base.ScreeningControl;
import constants.FilePaths;
import constants.StatusCodes;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.*;

import java.util.ArrayList;
import java.util.List;

// Removed unused static import
// import static base.ScreeningControl.jobRoleID;

// Add Allure imports
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Candidate Screening")
@Feature("Answer Submission Tests")
public class CandidateScreeningTest {

    @Test
    @Story("MCQ Screening Test")
    @Description("Test candidate screening flow with multiple question types: MCQ, Audio, Video, and Subjective")
    @Severity(SeverityLevel.CRITICAL)
    public void candidateMCQScreeningTest() {

        Allure.step("Set up candidate and job identifiers", () -> {
            ScreeningControl.candidateScreeningId = "694770d452e582fefe7e1ebc";
            ScreeningControl.jobRoleID = "68943b744df518afa9442034";
            ScreeningControl.jobApplicationID = "69476ed0c41f0cc9ce8110f0";
        });
        final String experience = "4.0";
        final String screenTestID = "692d3aeb3d9838750c2d650a";

        ScreeningDetailsServices screeningDetailsServices = new ScreeningDetailsServices();
        final Response screeningDetailsResponse = Allure.step("Fetch screening details and questions", () ->
                screeningDetailsServices.getScreeningDetails(screenTestID)
        );

        // Extract IDs (some may be unused depending on test scope)
        Allure.step("Extract question IDs", () -> {
            // These lines execute within the step but values are used later as needed
            screeningDetailsResponse.jsonPath().getString("find { it.mcq != null }.mcq[0]._id");
            screeningDetailsResponse.jsonPath().getString("find { it.audio != null }.audio[0]._id");
            screeningDetailsResponse.jsonPath().getString("find { it.video != null }.video[0]._id");
            screeningDetailsResponse.jsonPath().getString("find { it.subjective != null }.subjective[0]._id");
            screeningDetailsResponse.jsonPath().getString("find { it.programming != null }.programming[0]._id");
        });

        final String audioQuestionID = screeningDetailsResponse.jsonPath().getString("find { it.audio != null }.audio[0]._id");
        final String videoQuestionID = screeningDetailsResponse.jsonPath().getString("find { it.video != null }.video[0]._id");
        final String subjectiveQuestionID = screeningDetailsResponse.jsonPath().getString("find { it.subjective != null }.subjective[0]._id");
        final List<String> mcqIds = screeningDetailsResponse.jsonPath().getList("find { it.mcq != null }.mcq._id");

        // ...existing code...
        final List<String> answers = new ArrayList<>();
        answers.add("SignatureException");

        AudioService audioService = new AudioService();
        Response audioAnswerResponse = Allure.step("Submit Audio answer", () ->
                audioService.submitAudioAnswer(audioQuestionID, experience, "files/AudioAnswer.mp3")
        );
        Allure.step("Validate Audio answer response and schema", () -> {
            Assert.assertEquals(audioAnswerResponse.statusCode(), StatusCodes.STATUS_CODE_OK, "Expected HTTP 200 when submitting Audio answer");
            audioService.validateSchema(audioAnswerResponse, FilePaths.SCHEMA_FILE_FOR_VIDEO_AUDIO_MCQ);
        });

        SubjectiveService subjectiveService = new SubjectiveService();
        Response subjectiveAsnwerResponse = Allure.step("Submit Subjective answer", () ->
                subjectiveService.submitSubjectiveAnswer(subjectiveQuestionID, experience, "<p>- More Reliable</p><p>- More Secure</p><p>- Flackness is lesser then UI</p><p>- Cost Effiective Early Bugs identification</p>")
        );
        Allure.step("Validate Subjective answer response and schema", () -> {
            Assert.assertEquals(subjectiveAsnwerResponse.statusCode(), StatusCodes.STATUS_CODE_OK, "Expected HTTP 200 when submitting Subjective answer");
            subjectiveService.validateSchema(subjectiveAsnwerResponse, FilePaths.SCHEMA_FILE_FOR_SUBJECTIVE_PROGRAMMING);
        });

        VideoServices videoServices = new VideoServices();
        Response videoAnswerResponse = Allure.step("Submit Video answer", () ->
                videoServices.submitVideoAnswer(videoQuestionID, experience, "files/VideoAnswer.mp4")
        );
        Allure.step("Validate Video answer response and schema", () -> {
            Assert.assertEquals(videoAnswerResponse.statusCode(), StatusCodes.STATUS_CODE_OK, "Expected HTTP 200 when submitting Audio answer");
            videoServices.validateSchema(videoAnswerResponse, FilePaths.SCHEMA_FILE_FOR_VIDEO_AUDIO_MCQ);
        });

        Allure.step("Submit and validate all MCQ answers", () -> {
            for (int i = 0; i < mcqIds.size(); i++) {
                MCQService mcqService = new MCQService();
                Response submitMCQAnswerResponse = mcqService.submitMCQAnswer(mcqIds.get(i), experience, answers.get(i));
                Assert.assertEquals(submitMCQAnswerResponse.statusCode(), StatusCodes.STATUS_CODE_OK, "Expected HTTP 200 when submitting MCQ answer");
                mcqService.validateSchema(submitMCQAnswerResponse, FilePaths.SCHEMA_FILE_FOR_VIDEO_AUDIO_MCQ);
            }
        });

//        ProgrammingService programmingService = new ProgrammingService();
//        Response programmingResponse = programmingService.submitProgrammingAnswer("694770d452e582fefe7e1ebc","692d3aee3d9838750c2d6524","68943b744df518afa9442034","4.0","69476ed0c41f0cc9ce8110f0","694770d452e582fefe7e1ebc");
//        Assert.assertEquals(programmingResponse.statusCode(), StatusCodes.STATUS_CODE_OK, "Expected HTTP 200 when submitting Audio answer");
    }

}
