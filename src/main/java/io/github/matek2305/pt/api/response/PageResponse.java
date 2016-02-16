package io.github.matek2305.pt.api.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
public class PageResponse<T> extends ResourceSupport {

    private static final String PREV_PAGE_LINK = "prevPage";
    private static final String NEXT_PAGE_LINK = "nextPage";

    private final List<T> content;
    private final long totalElements;

    public void addPrevPageLink(Object invocationValue) {
        add(linkTo(invocationValue).withRel(PREV_PAGE_LINK));
    }

    public void addNextPageLink(Object invocationValue) {
        add(linkTo(invocationValue).withRel(NEXT_PAGE_LINK));
    }
}
