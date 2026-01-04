package entity.programmingPOJO;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.common.TypingAnalysis;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Payload for programming answer submission
 * Uses shared TypingAnalysis from entity.common package
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgrammingPayload {
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
    private TypingAnalysis typingAnalysis;
    private Boolean hasTypingAnalysis;
    private String screeningTestId;
    private Integer programmingLanguageId;
    private List<Object> editorEvents;
    private Integer retakes;
}
