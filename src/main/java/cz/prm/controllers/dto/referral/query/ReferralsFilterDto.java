package cz.prm.controllers.dto.referral.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReferralsFilterDto {

    private String globalFilter;

    private String name;

    private String status;
}
