package cz.prm.domain;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GROUP_TYPE_ROLE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTypeRole {

   @Id
   @GeneratedValue
   @Column(name = "GROUP_TYPE_ROLE_ID")
   private Long groupTypeRoleId;

   @Column(name = "LABEL")
   private String label;

   @Column(name = "POSITION")
   private Integer position;

   @ManyToOne(fetch = LAZY, cascade = ALL)
   @JoinColumn(name = "GROUP_TYPE_ID")
   private GroupType groupType;


}


