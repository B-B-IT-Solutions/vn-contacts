package cz.prm.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

   @Column(name = "LOCALE")
   private String locale;

   @Column(name = "TIMEZONE")
   private String timezone;

   @Column(name = "DATE_FORMAT")
   private String dateFormat;

   @Column(name = "NUMBER_FORMAT")
   private String numberFormat;

   @Column(name = "DISTANCE_FORMAT")
   private String distanceFormat;

   @Column(name = "CONTACT_SORT_ORDER")
   private String contactSortOrder;

   @OneToOne(fetch = LAZY)
   @JoinColumn(name = "ACCOUNT_ID")
   private Account account;

}
