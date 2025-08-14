package cz.prm.services.networking;

import static java.util.stream.Collectors.toList;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralSuggestion;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReferralSuggestionService {

    public List<ReferralSuggestion> generateSuggestions(Contact targetContact, List<Contact> allContacts) {
        List<ReferralSuggestion> suggestions = new ArrayList<>();

        for (Contact contact : allContacts) {
            if (contact.getContactId().equals(targetContact.getContactId())) {
                continue;
            }

            List<String> reasons = new ArrayList<>();
            int matchScore = 0;

            // Shared industries
            List<String> sharedIndustries = contact.getIndustries().stream().filter(targetContact.getIndustries()::contains).collect(toList());
            if (!sharedIndustries.isEmpty()) {
                reasons.add("Both work in " + String.join(", ", sharedIndustries) + " industries");
                matchScore += sharedIndustries.size() * 20;
            }

            // Target's contactIdeal clients match contact's services
            boolean clientServiceMatch = targetContact.getTargetMarkets().stream().anyMatch(client -> contact.getProducts().stream().anyMatch(
                service -> service.toLowerCase().contains(client.toLowerCase().split(" ")[0]) || client.toLowerCase()
                    .contains(service.toLowerCase().split(" ")[0])));
            if (clientServiceMatch) {
                reasons.add("Their services align with your target clients");
                matchScore += 25;
            }

            // Contact's contactIdeal clients match target's services
            boolean serviceClientMatch = contact.getTargetMarkets().stream().anyMatch(client -> targetContact.getProducts().stream().anyMatch(
                service -> service.toLowerCase().contains(client.toLowerCase().split(" ")[0]) || client.toLowerCase()
                    .contains(service.toLowerCase().split(" ")[0])));
            if (serviceClientMatch) {
                reasons.add("Your services align with their target clients");
                matchScore += 25;
            }

            // Complementary services
            List<String> complementaryServices = contact.getProducts().stream().filter(
                service -> !targetContact.getProducts().contains(service) && targetContact.getIndustries().stream()
                    .anyMatch(contact.getIndustries()::contains)).collect(toList());
            if (!complementaryServices.isEmpty()) {
                reasons.add("Offers complementary services you don't provcontactIde");
                matchScore += 15;
            }

//            // Connection strength bonus
//            if ("Strong".equalsIgnoreCase(contact.connectionStrength)) {
//                matchScore += 10;
//                reasons.add("Strong existing relationship");
//            }
//
//            // Location proximity bonus
//            String targetRegion = targetContact.location.contains(",") ? targetContact.location.split(",")[1].trim() : "";
//            if (!targetRegion.isEmpty() && contact.location.contains(targetRegion)) {
//                reasons.add("Located in same region");
//                matchScore += 5;
//            }

            if (matchScore > 20 && !reasons.isEmpty()) {
                suggestions.add(new ReferralSuggestion(contact, matchScore, reasons));
            }
        }

        return suggestions.stream().sorted((a, b) -> Integer.compare(b.getScore(), a.getScore())).limit(6).collect(toList());
    }
}
