package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "module", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {

   @Id
   @GeneratedValue
   private Long id;

   @Column(name = "MODULE_ID")
   private Long moduleId;

   @Column(name = "NAME")
   private String name;

   @Column(name = "NAME_TRANSLATION_KEY")
   private String nameTranslationKey;

   @Column(name = "TYPE")
   private String type;

   @Column(name = "CAN_BE_DELETED")
   private boolean canBeDeleted;

   @Column(name = "RESERVED_TO_CONTACT_INFORMATION")
   private boolean reservedToContactInformation;

   @Column(name = "PAGINATION")
   private boolean pagination;

   @ManyToOne
   private Account account;

   @OneToMany(mappedBy = "module")
   private Set<ModuleRow> rows;

   @ManyToMany
   @JoinTable(
       name = "module_template_page",
       joinColumns = @JoinColumn(name = "module_id"),
       inverseJoinColumns = @JoinColumn(name = "template_page_id")
   )
   private Set<TemplatePage> templatePages;


}


