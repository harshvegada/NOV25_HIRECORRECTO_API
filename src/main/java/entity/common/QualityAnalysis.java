package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared POJO for quality analysis in typing-based answers (Subjective, Programming)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualityAnalysis {
    private Integer wordCount;
    private Double averageWordsPerMinute;
    private Boolean hasProperStructure;
    private Boolean hasVariedVocabulary;
    private Integer qualityScore;
    private String riskLevel;

    public static QualityAnalysis createDefault() {
        return QualityAnalysis.builder()
                .wordCount(0)
                .averageWordsPerMinute(0.0)
                .hasProperStructure(false)
                .hasVariedVocabulary(false)
                .qualityScore(0)
                .riskLevel("low")
                .build();
    }
}

