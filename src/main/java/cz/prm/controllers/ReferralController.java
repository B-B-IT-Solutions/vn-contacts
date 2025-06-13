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
    public PageDto<ReferralDto> getReminders(@PathVariable("contactId") Long contactId, ReferralQueryDto queryDto) {
        var query = mapper.toNullSafeRemindersQuery(queryDto);
        var reminders = referralService.getReminders(contactId, query);
        return mapper.toPageDto(reminders);
    }

    @GetMapping("/reminder/{reminderId}")
    public ReferralDto getReminder(@PathVariable("reminderId") Long reminderId) {
        var reminder = referralService.getReminder(reminderId);
        return mapper.toReminderDto(reminder);
    }

    @PostMapping("/reminder")
    public void createReminder(@RequestBody ReferralDto dto) {
        var reminder = mapper.toReminder(dto);
        referralService.createReminder(reminder);
    }

    @PutMapping("/reminder/{reminderId}")
    public void updateReminder(@PathVariable("reminderId") Long reminderId, @RequestBody ReferralDto dto) {
        var reminder = mapper.toReminder(dto);
        referralService.updateReminder(reminderId, reminder);
    }

    @DeleteMapping("/reminder/{reminderId}")
    public void deleteReminder(@PathVariable("reminderId") Long reminderId) {
        referralService.deleteReminder(reminderId);
    }
}
