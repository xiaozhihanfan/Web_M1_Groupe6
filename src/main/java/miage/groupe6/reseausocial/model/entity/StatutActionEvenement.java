package miage.groupe6.reseausocial.model.entity;

/**
 * Enumération représentant les différents statuts possibles 
 * pour l’action d’un utilisateur vis-à-vis d’un événement.
 * <p>
 * Utilisé dans {@link ActionEvenement}.
 *
 * <ul>
 *     <li>{@code INSCRIRE} : L'utilisateur est inscrit à l'événement.</li>
 *     <li>{@code INTERESSER} : L'utilisateur a exprimé son intérêt sans s'inscrire.</li>
 * </ul>
 *
 * @see ActionEvenement
 */
public enum StatutActionEvenement {
    /**
     * L'utilisateur est inscrit à l'événement.
     */
    INSCRIRE,

    /**
     * L'utilisateur est intéressé mais pas encore inscrit.
     */
    INTERESSER
}
