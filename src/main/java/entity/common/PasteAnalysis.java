package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared POJO for paste analysis in typing-based answers (Subjective, Programming)
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasteAnalysis {
    private Integer totalPasteEvents;
    private Integer totalPastedCharacters;
    private Integer pastePercentage;
    private Integer rawPastePercentage;
    private Integer largestPaste;
    private Boolean hasCodePatterns;
    private Boolean hasFormatting;
    private String riskLevel;

    public static PasteAnalysis createDefault() {
        return PasteAnalysis.builder()
                .totalPasteEvents(0)
                .totalPastedCharacters(0)
                .pastePercentage(0)
                .rawPastePercentage(0)
                .largestPaste(0)
                .hasCodePatterns(false)
                .hasFormatting(false)
                .riskLevel("low")
                .build();
    }
}

