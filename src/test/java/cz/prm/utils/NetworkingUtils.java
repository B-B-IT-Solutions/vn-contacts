package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.COMMON_INDUSTRIES;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.COMPLEMENTARY_SERVICES;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.TARGET_MARKETS_PRODUCTS_MATCH;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.uuid;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.domain.contacts.networking.ReferralSuggestion;
import java.util.List;

public class NetworkingUtils {

    public static Contact contactPotentialReferral(Contact contact) {
        return contactPotentialReferrals(contact, 1).get(0);
    }

    public static List<Contact> contactPotentialReferrals(Contact contact, int count) {
        var industries = contact.getIndustries();
        var industry1 = industries.get(0);
        var industry2 = industries.get(1);
        var products = contact.getProducts();
        var product1 = products.get(0);

        return range(0, count).mapToObj((i) -> {
            var pr = contact();
            pr.getIndustries().addAll(newArrayList(industry1, industry2));
            if (i % 3 == 1) {
                pr.getProducts().add(product1);
                pr.getTargetMarkets().add(product1);
            }
            return pr;
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
        rs.addJustification(COMPLEMENTARY_SERVICES, uuid());
        rs.addJustification(TARGET_MARKETS_PRODUCTS_MATCH, uuid());
        rs.addCheckedCriteria(COMMON_INDUSTRIES);
        rs.addCheckedCriteria(COMPLEMENTARY_SERVICES);
        rs.addCheckedCriteria(TARGET_MARKETS_PRODUCTS_MATCH);
        return rs;
    }
}
