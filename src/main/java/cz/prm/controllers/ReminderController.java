package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.controllers.mappers.ReminderMapper;
import cz.prm.services.ReminderService;
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
public class ReminderController {

    private ReminderService reminderService;
    private ReminderMapper mapper;

    @Autowired
    public ReminderController(ReminderService reminderService, ReminderMapper mapper) {
        this.reminderService = reminderService;
        this.mapper = mapper;
    }

    @GetMapping("/contact/{contactId}")
    public PageDto<ReminderDto> getReminders(@PathVariable("contactId") Long contactId, RemindersQueryDto queryDto) {
        var query = mapper.toNullSafeRemindersQuery(queryDto);
        var reminders = reminderService.getReminders(contactId, query);
        return mapper.toPageDto(reminders);
    }

    @GetMapping("/reminder/{reminderId}")
    public ReminderDto getReminder(@PathVariable("reminderId") Long reminderId) {
        var reminder = reminderService.getReminder(reminderId);
        return mapper.toReminderDto(reminder);
    }

    @PostMapping("/reminder")
    public void createReminder(@RequestBody ReminderDto dto) {
        var reminder = mapper.toReminder(dto);
        reminderService.createReminder(reminder);
    }

    @PutMapping("/reminder/{reminderId}")
    public void updateReminder(@PathVariable("reminderId") Long reminderId, @RequestBody ReminderDto dto) {
        var reminder = mapper.toReminder(dto);
        reminderService.updateReminder(reminderId, reminder);
    }

    @DeleteMapping("/reminder/{reminderId}")
    public void deleteReminder(@PathVariable("reminderId") Long reminderId) {
        reminderService.deleteReminder(reminderId);
    }
}
