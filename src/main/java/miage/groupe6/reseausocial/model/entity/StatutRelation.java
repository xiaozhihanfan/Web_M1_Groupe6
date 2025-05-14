package miage.groupe6.reseausocial.model.entity;

/**
 * Énumération représentant le statut d'une relation d'amitié entre deux utilisateurs.
 * <p>
 * Permet de déterminer si la relation est en attente de traitement ou déjà acceptée.
 */
public enum StatutRelation {

    /**
     * La demande d'amitié a été acceptée par le destinataire.
     */
    ACCEPTEE,

    /**
     * La demande d'amitié a été envoyée mais n'a pas encore été traitée.
     */
    TRAITEE
}

