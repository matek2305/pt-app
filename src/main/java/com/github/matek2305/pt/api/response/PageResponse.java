package com.github.matek2305.pt.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageResponse<T> extends ResourceSupport {

    private final List<T> content;
    private final long totalElements;

    public void addPrevPageLink(PageResponse<T> getPageValue) {
        add(linkTo(getPageValue).withRel("prevPage"));
    }

    public void addNextPageLink(PageResponse<T> getPageValue) {
        add(linkTo(getPageValue).withRel("nextPage"));
    }
}
