package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "module_row_fields", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRowField {

   @Id
   @GeneratedValue
   private Long id;

   @Column(name = "module_row_id")
   private Long moduleRowId;

   @Column(name = "label")
   private String label;

   @Column(name = "module_field_type")
   private String moduleFieldType;

   @Column(name = "required")
   private boolean required;

   @Column(name = "position")
   private int position;

   @ManyToOne
   @JoinColumn(name = "module_row_id", insertable = false, updatable = false)
   private ModuleRow row;

   public static final String TYPE_INPUT_TEXT = "input_text";


}


