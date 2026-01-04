package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared POJO for copy-paste correlations in typing-based answers (Subjective, Programming)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyPasteCorrelations {
    private Integer totalCorrelations;
    private Integer questionPasteCount;
    private Integer averageTimeBetween;
    private String riskLevel;

    public static CopyPasteCorrelations createDefault() {
        return CopyPasteCorrelations.builder()
                .totalCorrelations(0)
                .questionPasteCount(0)
                .averageTimeBetween(0)
                .riskLevel("low")
                .build();
    }
}

