package cz.prm.domain;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GROUP_TYPE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupType {

   @Id
   @GeneratedValue
   @Column(name = "GROUP_TYPE_ID")
   private Long groupTypeId;

   @Column(name = "LABEL")
   private String label;

   @Column(name = "POSITION")
   private Integer position;

   @ManyToOne(fetch = LAZY)
   private Account account;

   @OneToMany(mappedBy = "groupType", fetch = LAZY, cascade = ALL)
   private Set<GroupTypeRole> groupTypeRoles;

}


