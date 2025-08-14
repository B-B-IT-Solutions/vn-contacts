package cz.prm.utils;

import static cz.prm.utils.ContactUtils.contact;

import cz.prm.domain.networking.ReferralRequirement;

public class NetworkingUtils {

    public static ReferralRequirement referralRequirement() {
        var rr = new ReferralRequirement();
        rr.setContact(contact());
        return rr;
    }
}
