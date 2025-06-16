package cz.prm.controllers.dto.referral;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.prm.domain.common.Priority;
import cz.prm.domain.referral.ReferralStatus;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralDto {

    @JsonProperty("referralId")
    private Long referralId;

    @JsonProperty("contactId")
    private Long contactId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("note")
    private String note;

    @JsonProperty("source")
    private String source;

    @JsonProperty("priority")
    private Priority priority;

    @JsonProperty("status")
    private ReferralStatus status;

    @JsonProperty("closedDate")
    private Instant closedDate;

    @JsonProperty("startDate")
    private Instant startDate;

    @JsonProperty("expiredDate")
    private Instant expiredDate;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
