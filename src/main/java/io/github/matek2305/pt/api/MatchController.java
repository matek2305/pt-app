package io.github.matek2305.pt.api;

import io.github.matek2305.pt.api.response.ListResponse;
import io.github.matek2305.pt.domain.entity.Match;
import io.github.matek2305.pt.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping(value = "/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListResponse<Match> getMatchList(@RequestParam("page") final int page, @RequestParam("size") final int size) {
        Page<Match> matchPage = matchService.getMatchPage(page, size);
        return new ListResponse<>(matchPage.getContent(), matchPage.getTotalElements());
    }
}
