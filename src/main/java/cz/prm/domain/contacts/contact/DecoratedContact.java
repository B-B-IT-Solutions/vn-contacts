package cz.prm.domain.contacts.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecoratedContact {

    private Contact contact;

    private About about;
}
