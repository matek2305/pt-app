package com.github.matek2305.pt.api;

import com.github.matek2305.pt.api.resource.MatchResource;
import com.github.matek2305.pt.service.MatchService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RestController
@RequestMapping("/users/{username}")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsersController {

    private final MatchService matchService;

    @RequestMapping(value = "/incoming_matches", method = RequestMethod.GET)
    public List<MatchResource> getIncomingMatches(@PathVariable("username") String username) {
        return matchService.getIncomingMatchListFor(username).stream()
                .map(MatchResource::fromEntity)
                .collect(Collectors.toList());
    }
}
