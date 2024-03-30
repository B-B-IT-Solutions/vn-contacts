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
@Table(name = "group_types", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupType {

   @Id
   @GeneratedValue
   private Long id;

   @Column(name = "GROUP_TYPE_ID")
   private Long groupTypeId;

   @Column
   private String label;

   @Column(name = "label_translation_key")
   private String labelTranslationKey;

   @Column
   private Integer position;

   @ManyToOne(fetch = FetchType.LAZY)
   private Account account;

   @OneToMany(mappedBy = "groupType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private Set<GroupTypeRole> groupTypeRoles;

}


