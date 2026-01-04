package services;

import entity.common.CopyPasteCorrelations;
import entity.common.FocusAnalysis;
import entity.common.GlobalEventAnalysis;
import entity.common.PasteAnalysis;
import entity.common.QualityAnalysis;
import entity.common.TypingAnalysis;
import entity.programmingPOJO.ProgrammingPayload;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import utility.JavaToJSON;

import java.util.ArrayList;

/**
 * Service for handling programming answer submissions
 */
public class ProgrammingService extends BaseService {

    private static final int DEFAULT_TIME_SPENT = 73;
    private static final int DEFAULT_DURATION = 68166;

    @Step("Submit programming answer for question ID: {questionId}")
    public Response submitProgrammingAnswer(String candidateScreeningId, String questionId, String jobRoleId,
                                           String experience, String jobApplicationId, String screeningTestId) {
        String payload = buildProgrammingAnswerPayload(questionId, jobRoleId, experience, jobApplicationId, screeningTestId);
        return updateCandidateResult(candidateScreeningId, payload);
    }

    @Step("Build programming answer payload for question ID: {questionId}")
    private String buildProgrammingAnswerPayload(String questionId, String jobRoleId, String experience,
                                                  String jobApplicationId, String screeningTestId) {
        String candidateAnswer = getSampleProgrammingAnswer();

        ProgrammingPayload payload = ProgrammingPayload.builder()
                .questionId(questionId)
                .type("programming")
                .skill("api")
                .jobRoleId(jobRoleId)
                .experience(experience)
                .jobApplicationId(jobApplicationId)
                .timeSpent(DEFAULT_TIME_SPENT)
                .fullScreenExitCount(0)
                .tabSwitchCount(0)
                .candidateAnswer(candidateAnswer)
                .typingAnalysis(buildTypingAnalysis())
                .hasTypingAnalysis(true)
                .screeningTestId(screeningTestId)
                .programmingLanguageId(62)
                .editorEvents(new ArrayList<>())
                .retakes(1)
                .build();

        return JavaToJSON.convertToJSON(payload);
    }

    @Step("Build typing analysis for programming answer")
    private TypingAnalysis buildTypingAnalysis() {
        return TypingAnalysis.builder()
                .totalDuration(DEFAULT_DURATION)
                .totalCharacters(6161)
                .keystrokeCount(5)
                .pasteEventCount(0)
                .copyEventCount(0)
                .focusLossCount(0)
                .pasteAnalysis(PasteAnalysis.createDefault())
                .typingAnalysis(TypingAnalysis.createInnerTypingAnalysis(0.0, 5, 2))
                .focusAnalysis(FocusAnalysis.createDefault())
                .qualityAnalysis(QualityAnalysis.builder()
                        .wordCount(390)
                        .averageWordsPerMinute(343.28)
                        .hasProperStructure(true)
                        .hasVariedVocabulary(false)
                        .qualityScore(1)
                        .riskLevel("high")
                        .build())
                .globalEventAnalysis(GlobalEventAnalysis.createDefault())
                .copyPasteCorrelations(CopyPasteCorrelations.createDefault())
                .riskScore(25)
                .privacyCompliant(true)
                .build();
    }

    private String getSampleProgrammingAnswer() {
        return "import java.util.ArrayList;\n" +
                "import java.util.Collections;\n" +
                "import java.util.List;\n" +
                "import java.util.Scanner;\n" +
                "import java.util.Comparator;\n" +
                "import java.util.stream.Collectors;\n\n" +
                "class Patient {\n" +
                "    int id;\n" +
                "    String name;\n" +
                "    int age;\n\n" +
                "    public Patient(int id, String name, int age) {\n" +
                "        this.id = id;\n" +
                "        this.name = name;\n" +
                "        this.age = age;\n" +
                "    }\n\n" +
                "    public String getName() {\n" +
                "        return name;\n" +
                "    }\n\n" +
                "    public int getAge() {\n" +
                "        return age;\n" +
                "    }\n" +
                "}\n\n" +
                "public class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n\n" +
                "        int N = scanner.nextInt();\n" +
                "        List<Patient> patients = new ArrayList<>();\n" +
                "        for (int i = 0; i < N; i++) {\n" +
                "            int id = scanner.nextInt();\n" +
                "            String name = scanner.next();\n" +
                "            int age = scanner.nextInt();\n" +
                "            patients.add(new Patient(id, name, age));\n" +
                "        }\n\n" +
                "        int minAge = scanner.nextInt();\n" +
                "        scanner.close();\n\n" +
                "        // TODO: Implement the solution here\n" +
                "    }\n" +
                "}";
    }

}
