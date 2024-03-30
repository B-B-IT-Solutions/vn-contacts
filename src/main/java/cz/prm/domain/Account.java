package cz.prm.domain;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNT", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

   @Id
   @GeneratedValue
   @Column(name = "ACCOUNT_ID")
   private Long accountId;

   @OneToOne(mappedBy = "account")
   private User user;

   @OneToMany(mappedBy = "account")
   private List<Template> templates;

   @OneToMany(mappedBy = "account")
   private List<Module> modules;

   @OneToMany(mappedBy = "account")
   private List<GroupType> groupTypes;

   @OneToMany(mappedBy = "account")
   private List<RelationshipGroupType> relationshipGroupTypes;

   @Column(name = "gender")
   @Enumerated(STRING)
   private Gender gender;

   //   @OneToMany(mappedBy = "account")
//   private List<ContactInformationType> contactInformationTypes;
//
//   @OneToMany(mappedBy = "account")
//   private List<AddressType> addressTypes;
//
//   @OneToMany(mappedBy = "account")
//   private List<PetCategory> petCategories;
//
   @Column(name = "emotion")
   @Enumerated(STRING)
   private Emotion emotion;

//
//   @ManyToMany(mappedBy = "accounts")
//   private List<Currency> currencies;
//
//   @OneToMany(mappedBy = "account")
//   private List<CallReasonType> callReasonTypes;
//
//   @OneToMany(mappedBy = "account")
//   private List<GiftOccasion> giftOccasions;
//
//   @OneToMany(mappedBy = "account")
//   private List<GiftState> giftStates;
//
//   @OneToMany(mappedBy = "account")
//   private List<Vault> vaults;
//
//   @OneToMany(mappedBy = "account")
//   private List<PostTemplate> postTemplates;
//
//   @OneToMany(mappedBy = "account")
//   private List<Religion> religions;

}