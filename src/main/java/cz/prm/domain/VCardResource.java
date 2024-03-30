package cz.prm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

//@Entity
//@Table(name = "VCARD_RESOURCE", schema = "public")
//@DynamicInsert
//@DynamicUpdate
//@SQLDelete(sql = "UPDATE vcard_resource SET deleted_at = NOW() WHERE id = ?")
//@Where(clause = "deleted_at IS NULL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class VCardResource {



   @Column(name = "VAULT_ID")
   private String vaultId;

   @Column(name = "DISTANT_ETAG")
   private String distantEtag;

   @Column(name = "VCARD")
   private String vcard;

   @Column(name = "UPDATED_AT")
   @Temporal(TemporalType.TIMESTAMP)
   @UpdateTimestamp
   private Date updatedAt;

   @Column(name = "CREATED_AT")
   @Temporal(TemporalType.TIMESTAMP)
   @CreationTimestamp
   private Date createdAt;

   @Column(name = "DELETED_AT")
   @Temporal(TemporalType.TIMESTAMP)
   private Date deletedAt;


}

