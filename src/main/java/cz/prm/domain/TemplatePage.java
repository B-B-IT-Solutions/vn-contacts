package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEMPLATE_PAGE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplatePage {

   @Id
   @GeneratedValue
   @Column(name = "TEMPLATE_PAGE_ID")
   private Long templatePageId;

   @Column(name = "NAME")
   private String name;

   @Column(name = "POSITION")
   private Integer position;

   @Column(name = "SLUG")
   private String slug;

   @Column(name = "TYPE")
   private String type;

   @ManyToOne
   @JoinColumn(name = "template_id", referencedColumnName = "id")
   private Template template;

   @ManyToMany
   @JoinTable(
       name = "module_template_page",
       joinColumns = @JoinColumn(name = "template_page_id"),
       inverseJoinColumns = @JoinColumn(name = "module_id")
   )
   private List<Module> modules;

   public static final String TYPE_CONTACT = "contact_information";
   public static final String TYPE_FEED = "feed";


}