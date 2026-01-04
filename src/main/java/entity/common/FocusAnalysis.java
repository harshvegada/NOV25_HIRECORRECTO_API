package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared POJO for focus analysis in typing-based answers (Subjective, Programming)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FocusAnalysis {
    private Integer totalFocusEvents;
    private Integer focusLossCount;
    private Double focusChangeFrequency;
    private String riskLevel;

    public static FocusAnalysis createDefault() {
        return FocusAnalysis.builder()
                .totalFocusEvents(0)
                .focusLossCount(0)
                .focusChangeFrequency(0.0)
                .riskLevel("low")
                .build();
    }
}

