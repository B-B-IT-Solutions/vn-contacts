package cz.prm.domain.settings.contact;

import static com.google.common.collect.Lists.newArrayList;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class InitContactSettings {

    public static final List<Industry> INITIAL_INDUSTRIES = newArrayList(new Industry("Construction"), new Industry("Data Science"),
        new Industry("Education"), new Industry("Finance"), new Industry("Healthcare"), new Industry("Health & Fitness"),
        new Industry("Human Resources"), new Industry("Information Technology"), new Industry("Retail"), new Industry("Real estate"));
}
