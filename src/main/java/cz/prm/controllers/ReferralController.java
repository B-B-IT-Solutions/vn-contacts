package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.controllers.mappers.ReferralMapper;
import cz.prm.services.referral.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("referrals")
@RestController
public class ReferralController {

    private ReferralService referralService;
    private ReferralMapper referralMapper;

    @Autowired
    public ReferralController(ReferralService referralService, ReferralMapper referralMapper) {
        this.referralService = referralService;
        this.referralMapper = referralMapper;
    }

    @GetMapping
    public PageDto<ReferralDto> getReferrals(ReferralQueryDto queryDto) {
        var query = referralMapper.toNullSafeReferralsQuery(queryDto);
        var referrals = referralService.getReferrals(query);
        return referralMapper.toPageDto(referrals);
    }

    @GetMapping("/contact/{contactId}")
    public PageDto<ReferralDto> getContactReferrals(@PathVariable("contactId") Long contactId, ReferralQueryDto queryDto) {
        var query = referralMapper.toNullSafeReferralsQuery(queryDto);
        var referrals = referralService.getContactReferrals(contactId, query);
        return referralMapper.toPageDto(referrals);
    }

    @GetMapping("/referral/{referralId}")
    public ReferralDto getReferral(@PathVariable("referralId") Long referralId) {
        var referral = referralService.getReferral(referralId);
        return referralMapper.toReferralDto(referral);
    }

    @PostMapping("/referral")
    public void createReferral(@RequestBody ReferralDto dto) {
        var referral = referralMapper.toReferral(dto);
        referralService.createReferral(referral);
    }

    @PutMapping("/referral/{referralId}")
    public void updateReferral(@PathVariable("referralId") Long referralId, @RequestBody ReferralDto dto) {
        var referral = referralMapper.toReferral(dto);
        referralService.updateReferral(referralId, referral);
    }

    @DeleteMapping("/referral/{referralId}")
    public void deleteReferral(@PathVariable("referralId") Long referralId) {
        referralService.deleteReferral(referralId);
    }
}
