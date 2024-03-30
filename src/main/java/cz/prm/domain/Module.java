package cz.prm.domain;

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
@Table(name = "MODULE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {

   @Id
   @GeneratedValue
   @Column(name = "MODULE_ID")
   private Long moduleId;

   @Column(name = "NAME")
   private String name;

   @Column(name = "TYPE")
   private String type;

   @ManyToOne
   private Account account;

   @OneToMany(mappedBy = "module")
   private Set<ModuleRow> rows;


}


