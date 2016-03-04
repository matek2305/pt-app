package com.github.matek2305.pt.api.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@JsonTypeName("validationFailed")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ValidationFailedResponse {

    private final List<String> messageList;

    public ValidationFailedResponse(List<String> messageList) {
        this.messageList = messageList;
    }

    public ValidationFailedResponse(String message) {
        this(Collections.singletonList(message));
    }
}
