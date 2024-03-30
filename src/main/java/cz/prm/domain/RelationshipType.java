package cz.prm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RELATIONSHIP_TYPE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipType {

   // Constants
   public static final String TYPE_LOVE = "family";
   public static final String TYPE_CHILD = "child";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private String nameTranslationKey;
   private String nameReverseRelationship;
   private String nameReverseRelationshipTranslationKey;
   private boolean canBeDeleted;

   @ManyToOne(fetch = FetchType.LAZY)
   private RelationshipGroupType relationshipGroupType;

}





