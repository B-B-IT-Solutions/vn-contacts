package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEMPLATE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

   @Id
   @GeneratedValue
   private Long id;

   @Column(name = "TEMPLATE_ID")
   private Long templateId;

   @Column(name = "NAME")
   private String name;

   @Column(name = "NAME_TRANSLATION_KEY")
   private String nameTranslationKey;

   @Column(name = "CAN_BE_DELETED")
   private boolean canBeDeleted;

   @ManyToOne
   private Account account;

   @OneToMany
   private List<TemplatePage> pages;

   @OneToMany
   private List<Contact> contacts;


}