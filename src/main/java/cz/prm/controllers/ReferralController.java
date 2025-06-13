package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.controllers.mappers.ReferralMapper;
import cz.prm.services.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("reminders")
@RestController
public class ReferralController {

    private ReferralService referralService;
    private ReferralMapper mapper;

    @Autowired
    public ReferralController(ReferralService referralService, ReferralMapper mapper) {
        this.referralService = referralService;
        this.mapper = mapper;
    }

    @GetMapping("/contact/{contactId}")
    public PageDto<ReferralDto> getReferrals(@PathVariable("contactId") Long contactId, ReferralQueryDto queryDto) {
        var query = mapper.toNullSafeReferralsQuery(queryDto);
        var reminders = referralService.getReferrals(contactId, query);
        return mapper.toPageDto(reminders);
    }

    @GetMapping("/reminder/{reminderId}")
    public ReferralDto getReferral(@PathVariable("reminderId") Long reminderId) {
        var reminder = referralService.getReferral(reminderId);
        return mapper.toReferralDto(reminder);
    }

    @PostMapping("/reminder")
    public void createReferral(@RequestBody ReferralDto dto) {
        var reminder = mapper.toReferral(dto);
        referralService.createReferral(reminder);
    }

    @PutMapping("/reminder/{reminderId}")
    public void updateReferral(@PathVariable("reminderId") Long reminderId, @RequestBody ReferralDto dto) {
        var reminder = mapper.toReferral(dto);
        referralService.updateReferral(reminderId, reminder);
    }

    @DeleteMapping("/reminder/{reminderId}")
    public void deleteReferral(@PathVariable("reminderId") Long reminderId) {
        referralService.deleteReferral(reminderId);
    }
}
