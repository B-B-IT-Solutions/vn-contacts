package cz.prm.domain;

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
@Table(name = "MODULE_ROW_FIELDS", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRowField {

   @Id
   @GeneratedValue
   @Column(name = "MODULE_ROW_FIELD_ID")
   private Long moduleRowFieldId;

   @Column(name = "LABEL")
   private String label;

   @Column(name = "MODULE_FIELD_TYPE")
   private String moduleFieldType;

   @Column(name = "REQUIRED")
   private boolean required;

   @Column(name = "POSITION")
   private int position;

   @ManyToOne
   @JoinColumn(name = "MODULE_ROW_ID")
   private ModuleRow row;

   public static final String TYPE_INPUT_TEXT = "input_text";


}


