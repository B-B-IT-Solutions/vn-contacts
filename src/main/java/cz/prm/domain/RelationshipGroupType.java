package cz.prm.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "relationship_group_type", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipGroupType {

   @Id
   @GeneratedValue
   private Long id;

   @Column(name = "RELATIONSHIP_GROUP_TYPE_ID")
   private Long relationshipGroupTypeId;

   @Column(name = "name")
   private String name;

   @Column(name = "name_translation_key")
   private String nameTranslationKey;

   @Column(name = "type")
   private String type;

   @Column(name = "can_be_deleted")
   private boolean canBeDeleted;

   @ManyToOne(fetch = FetchType.LAZY)
   private Account account;

   @OneToMany(mappedBy = "relationshipGroupType", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<RelationshipType> types;

   public static final String TYPE_FAMILY = "family";
   public static final String TYPE_LOVE = "love";

}



