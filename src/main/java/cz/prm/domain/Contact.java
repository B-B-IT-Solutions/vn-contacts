package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CONTACT", schema = "public")
public class Contact extends VCardResource {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "ID", nullable = false, unique = true, length = 36)
   private String id;

}



