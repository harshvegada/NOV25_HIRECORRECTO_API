package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared POJO for global event analysis in typing-based answers (Subjective, Programming)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalEventAnalysis {
    private Integer globalCopyCount;
    private Integer questionCopyCount;
    private Integer externalInteractionCount;
    private Boolean hasQuestionCopying;
    private Boolean hasHighRiskCopying;
    private CopySourceDistribution copySourceDistribution;
    private Integer suspiciousPatternCount;
    private String riskLevel;

    public static GlobalEventAnalysis createDefault() {
        return GlobalEventAnalysis.builder()
                .globalCopyCount(0)
                .questionCopyCount(0)
                .externalInteractionCount(0)
                .hasQuestionCopying(false)
                .hasHighRiskCopying(false)
                .copySourceDistribution(CopySourceDistribution.builder().build())
                .suspiciousPatternCount(0)
                .riskLevel("low")
                .build();
    }
}

