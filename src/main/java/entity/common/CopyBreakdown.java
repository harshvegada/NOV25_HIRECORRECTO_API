package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared POJO for copy breakdown tracking across all answer types
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyBreakdown {
    private QuestionCopies questionCopies;
    private OptionCopies optionCopies;
    private FullQuestionCopies fullQuestionCopies;

    public static CopyBreakdown createDefault() {
        return CopyBreakdown.builder()
                .questionCopies(QuestionCopies.createDefault())
                .optionCopies(OptionCopies.createDefault())
                .fullQuestionCopies(FullQuestionCopies.createDefault())
                .build();
    }
}

