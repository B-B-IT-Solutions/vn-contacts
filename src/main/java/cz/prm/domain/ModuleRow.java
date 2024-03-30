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
@Table(name = "MODULE_ROW", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRow {

   @Id
   @GeneratedValue
   @Column(name = "MODULE_ROW_ID")
   private Long moduleRowId;

   @Column(name = "POSITION")
   private Integer position;

   @OneToMany(mappedBy = "row", fetch = LAZY, cascade = ALL, orphanRemoval = true)
   private Set<ModuleRowField> fields;

   @ManyToOne(fetch = LAZY)
   private Module module;

}