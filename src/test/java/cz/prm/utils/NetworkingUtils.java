package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.uuids;

import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import java.util.List;

public class NetworkingUtils {

    public static ReferralRequirement referralRequirement() {
        var rr = new ReferralRequirement();
        rr.setContact(contact());
        return rr;
    }

    public static List<ReferralSuggestion> referralSuggestions() {
        return newArrayList(referralSuggestion(), referralSuggestion(), referralSuggestion());
    }

    public static ReferralSuggestion referralSuggestion() {
        var rs = new ReferralSuggestion();
        rs.setContact(contact());
        rs.setScore(randomInt());
        rs.setReasons(uuids());
        return rs;
    }
}
