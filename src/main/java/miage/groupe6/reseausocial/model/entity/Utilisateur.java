package miage.groupe6.reseausocial.model.entity;

import java.sql.Date;
import jakarta.persistence.*;


@Entity
@Table(name = "utilisateur")
public class Utilisateur {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long idU;


   private String nomU;
   private String prenomU;
   private String emailU;
   private String mpU;
   private String descriptionU;
   private String avatarU;
   private Date dateInscription;


   // === Getters et Setters ===

   /**
    * @return Long return the idU
    */
   public Long getIdU() {
       return idU;
   }


   /**
    * @param idU the idU to set
    */
   public void setIdU(Long idU) {
       this.idU = idU;
   }


   /**
    * @return String return the nomU
    */
   public String getNomU() {
       return nomU;
   }


   /**
    * @param nomU the nomU to set
    */
   public void setNomU(String nomU) {
       this.nomU = nomU;
   }


   /**
    * @return String return the prenomU
    */
   public String getPrenomU() {
       return prenomU;
   }


   /**
    * @param prenomU the prenomU to set
    */
   public void setPrenomU(String prenomU) {
       this.prenomU = prenomU;
   }


   /**
    * @return String return the emailU
    */
   public String getEmailU() {
       return emailU;
   }


   /**
    * @param emailU the emailU to set
    */
   public void setEmailU(String emailU) {
       this.emailU = emailU;
   }


   /**
    * @return String return the mpU
    */
   public String getMpU() {
       return mpU;
   }


   /**
    * @param mpU the mpU to set
    */
   public void setMpU(String mpU) {
       this.mpU = mpU;
   }


   /**
    * @return String return the descriptionU
    */
   public String getDescriptionU() {
       return descriptionU;
   }


   /**
    * @param descriptionU the descriptionU to set
    */
   public void setDescriptionU(String descriptionU) {
       this.descriptionU = descriptionU;
   }


   /**
    * @return String return the avatarU
    */
   public String getAvatarU() {
       return avatarU;
   }


   /**
    * @param avatarU the avatarU to set
    */
   public void setAvatarU(String avatarU) {
       this.avatarU = avatarU;
   }


   /**
    * @return Date return the dateInscription
    */
   public Date getDateInscription() {
       return dateInscription;
   }


   /**
    * @param dateInscription the dateInscription to set
    */
   public void setDateInscription(Date dateInscription) {
       this.dateInscription = dateInscription;
   }


}
