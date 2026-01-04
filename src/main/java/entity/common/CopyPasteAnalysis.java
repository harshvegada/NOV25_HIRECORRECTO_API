package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Shared POJO for copy-paste analysis across all answer types (MCQ, Audio, Video)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyPasteAnalysis {
    private Integer totalDuration;
    private Integer totalCopyEvents;
    private Integer questionCopyCount;
    private Integer optionCopyCount;
    private Integer fullQuestionCopyCount;
    private Boolean hasQuestionCopying;
    private Boolean hasOptionCopying;
    private Boolean hasFullQuestionCopying;
    private Integer riskScore;
    private String riskLevel;
    private Boolean isSuspicious;
    private CopyBreakdown copyBreakdown;
    private String sessionId;
    private String analysisVersion;
    private String timestamp;

    /**
     * Creates a default low-risk CopyPasteAnalysis with no suspicious activity
     */
    public static CopyPasteAnalysis createDefault(int totalDuration) {
        return CopyPasteAnalysis.builder()
                .totalDuration(totalDuration)
                .totalCopyEvents(0)
                .questionCopyCount(0)
                .optionCopyCount(0)
                .fullQuestionCopyCount(0)
                .hasQuestionCopying(false)
                .hasOptionCopying(false)
                .hasFullQuestionCopying(false)
                .riskScore(0)
                .riskLevel("low")
                .isSuspicious(false)
                .copyBreakdown(CopyBreakdown.createDefault())
                .sessionId(generateSessionId())
                .analysisVersion("2.0-mcq-focused")
                .timestamp(Instant.now().toString())
                .build();
    }

    private static String generateSessionId() {
        return "copypaste_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}

