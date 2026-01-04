package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Shared POJO for typing analysis in text-based answers (Subjective, Programming)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypingAnalysis {
    private Integer totalDuration;
    private Integer totalCharacters;
    private Integer keystrokeCount;
    private Integer pasteEventCount;
    private Integer copyEventCount;
    private Integer focusLossCount;
    private PasteAnalysis pasteAnalysis;
    private TypingAnalysis typingAnalysis;  // Nested for detailed metrics
    private FocusAnalysis focusAnalysis;
    private QualityAnalysis qualityAnalysis;
    private GlobalEventAnalysis globalEventAnalysis;
    private CopyPasteCorrelations copyPasteCorrelations;
    private Integer riskScore;
    private String sessionId;
    private String analysisVersion;
    private String timestamp;
    private Boolean privacyCompliant;
    private Double averageTypingSpeed;
    private Integer typingBursts;
    private Integer totalKeystrokes;
    private Integer backspaceCount;
    private String riskLevel;

    /**
     * Creates a default low-risk TypingAnalysis
     */
    public static TypingAnalysis createDefault(int totalDuration, int totalCharacters, int keystrokeCount) {
        return TypingAnalysis.builder()
                .totalDuration(totalDuration)
                .totalCharacters(totalCharacters)
                .keystrokeCount(keystrokeCount)
                .pasteEventCount(0)
                .copyEventCount(0)
                .focusLossCount(0)
                .pasteAnalysis(PasteAnalysis.createDefault())
                .focusAnalysis(FocusAnalysis.createDefault())
                .qualityAnalysis(QualityAnalysis.createDefault())
                .globalEventAnalysis(GlobalEventAnalysis.createDefault())
                .copyPasteCorrelations(CopyPasteCorrelations.createDefault())
                .riskScore(0)
                .sessionId(generateSessionId())
                .analysisVersion("2.0-enhanced")
                .timestamp(Instant.now().toString())
                .privacyCompliant(true)
                .riskLevel("low")
                .build();
    }

    /**
     * Creates a nested typing analysis for inner metrics
     */
    public static TypingAnalysis createInnerTypingAnalysis(double avgSpeed, int keystrokes, int backspaces) {
        return TypingAnalysis.builder()
                .averageTypingSpeed(avgSpeed)
                .typingBursts(0)
                .totalKeystrokes(keystrokes)
                .backspaceCount(backspaces)
                .riskLevel("low")
                .build();
    }

    private static String generateSessionId() {
        return "typing_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}

