package cz.prm.domain.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CONTACT", schema = "public")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

   @Id
   @GeneratedValue
   @Column(name = "CONTACT_ID")
   private Long contactId;

   @Column(name = "FIRST_NAME")
   private String firstName;

   @Column(name = "LAST_NAME")
   private String lastName;

   @Column(name = "EMAIL")
   private String email;

   @Column(name = "OWNER")
   private String owner;

}
