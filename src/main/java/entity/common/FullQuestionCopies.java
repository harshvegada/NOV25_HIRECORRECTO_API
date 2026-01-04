package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Shared POJO for full question copy tracking across all answer types
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullQuestionCopies {
    private Integer count;
    private Integer averageLength;
    private List<Object> timestamps;

    public static FullQuestionCopies createDefault() {
        return FullQuestionCopies.builder()
                .count(0)
                .averageLength(0)
                .timestamps(List.of())
                .build();
    }
}

