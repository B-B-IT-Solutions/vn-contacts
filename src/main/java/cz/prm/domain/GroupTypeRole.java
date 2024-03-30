package cz.prm.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_type_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTypeRole {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "group_type_role_id", nullable = false)
   private Long groupTypeRoleId;

   @Column(name = "label")
   private String label;

   @Column(name = "label_translation_key")
   private String labelTranslationKey;

   @Column(name = "position")
   private Integer position;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "group_type_id", referencedColumnName = "id", insertable = false, updatable = false)
   private GroupType groupType;


}


