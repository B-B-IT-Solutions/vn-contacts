package cz.prm.extensions.customizations;

import cz.prm.repositories.customizations.ContactCustomizationsRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestContactCustomizationsRepository extends ContactCustomizationsRepository {

}
