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
@Table(name = "MODULE_ROW", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRow {

   @Id
   @GeneratedValue
   private Long id;

   @Column(name = "MODULE_ROW_ID")
   private Long moduleRowId;

   @Column(name = "POSITION")
   private Integer position;

   @ManyToOne(fetch = FetchType.LAZY)
   private Module module;

   @OneToMany(mappedBy = "row", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
   private Set<ModuleRowField> fields;

}