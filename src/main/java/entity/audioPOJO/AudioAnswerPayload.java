package entity.audioPOJO;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.common.CopyPasteAnalysis;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Payload for audio answer analysis request
 * Uses shared CopyPasteAnalysis from entity.common package
 */
@Builder
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioAnswerPayload {
    private String fileId;
    private String question;
    private String questionId;
    private String candidateScreeningId;
    private String candidateApplicationId;
    private String skillName;
    private String type;
    private String jobRoleId;
    private String maxTime;
    private String experience;
    private CopyPasteAnalysis copyPasteAnalysis;
    private Boolean hasCopyPasteAnalysis;
    private Integer fullScreenExitCount;
    private Integer tabSwitchCount;
}
