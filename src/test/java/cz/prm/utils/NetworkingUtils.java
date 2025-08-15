package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.services.networking.data.scoring.ScoringCriteria.COMMON_INDUSTRIES;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.uuid;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import java.util.List;

public class NetworkingUtils {

    public static List<Contact> contactPotentialReferrals(Contact contact, int count) {
        var industries = contact.getIndustries();
        var industry1 = industries.get(0);
        var industry2 = industries.get(1);

        return range(0, count).mapToObj((i) -> {
            var potentialReferral = contact();
            potentialReferral.getIndustries().add(industry1);
            potentialReferral.getIndustries().add(industry2);
            return potentialReferral;
        }).collect(toList());
    }

    public static ReferralRequirement referralRequirement() {
        return referralRequirement(contact());
    }

    public static ReferralRequirement referralRequirement(Contact contact) {
        var rr = new ReferralRequirement();
        rr.setContact(contact);
        return rr;
    }

    public static List<ReferralSuggestion> referralSuggestions() {
        return newArrayList(referralSuggestion(), referralSuggestion(), referralSuggestion());
    }

    public static ReferralSuggestion referralSuggestion() {
        var rs = new ReferralSuggestion();
        rs.setContact(contact());
        rs.setScore(randomInt());
        rs.addJustification(COMMON_INDUSTRIES, uuid());
        rs.addCheckedCriteria(COMMON_INDUSTRIES);
        return rs;
    }
}
