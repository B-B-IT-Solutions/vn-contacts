package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
   @Column(name = "USER_ID")
   private Long userId;

   @Column(name = "FIRST_NAME")
   private String firstName;

   @Column(name = "LAST_NAME")
   private String lastName;

   @ManyToOne(fetch = FetchType.LAZY)
   private Account account;

}
