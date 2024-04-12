package cz.prm.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "USER", schema = "public")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
   @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "FIRST_NAME")
   private String firstName;

   @Column(name = "LAST_NAME")
   private String lastName;

   @Column(name = "EMAIL")
   private String email;

   @Column(name = "EMAIL_VERIFIED_AT")
   private Instant emailVerifiedAt;

   @Column(name = "PASSWORD")
   private String password;

}
