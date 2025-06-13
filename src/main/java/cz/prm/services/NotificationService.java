package cz.prm.services;

import cz.prm.domain.referral.query.ReferralsQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class NotificationService {

    private ReferralService referralService;

    @Autowired
    public NotificationService(ReferralService referralService) {
        this.referralService = referralService;
    }

    public void getNotifications(Long contactId) {
        var reminders = referralService.getReferrals(contactId, new ReferralsQuery());

        reminders.getContent().stream().filter(r -> r.hasActiveRecurrence()).forEach(r -> {
            var rule = r.getRecurrence();
        });
    }
}
