package entity.mcqPOJO;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.common.CopyPasteAnalysis;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Payload for MCQ answer submission
 * Uses shared CopyPasteAnalysis from entity.common package
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCQRootPayload {
    private String questionId;
    private String type;
    private String skill;
    private String jobRoleId;
    private String experience;
    private String jobApplicationId;
    private Integer timeSpent;
    private Integer fullScreenExitCount;
    private Integer tabSwitchCount;
    private String candidateAnswer;
    private CopyPasteAnalysis copyPasteAnalysis;
    private Boolean hasCopyPasteAnalysis;
}