package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.networking.ReferralSuggestionDto;
import cz.prm.controllers.mappers.contacts.NetworkingMapper;
import cz.prm.services.networking.NetworkingClearingHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("networking")
@RestController
public class NetworkingController {

    private NetworkingClearingHouse clearingHouse;
    private NetworkingMapper mapper;

    @Autowired
    public NetworkingController(NetworkingClearingHouse clearingHouse, NetworkingMapper mapper) {
        this.clearingHouse = clearingHouse;
        this.mapper = mapper;
    }

    @GetMapping("{contactId}/referral-suggestions")
    public PageDto<ReferralSuggestionDto> getReferralSuggestions(@PathVariable("contactId") Long contactId) {
        var dc = clearingHouse.getReferralSuggestions(contactId);
        return mapper.toPageDto(dc);
    }
}
