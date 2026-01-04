package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all answer payloads containing common fields
 */
@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseAnswerPayload {
    protected String questionId;
    protected String type;
    protected String skill;
    protected String jobRoleId;
    protected String experience;
    protected String jobApplicationId;
    protected Integer timeSpent;
    protected Integer fullScreenExitCount;
    protected Integer tabSwitchCount;
}

