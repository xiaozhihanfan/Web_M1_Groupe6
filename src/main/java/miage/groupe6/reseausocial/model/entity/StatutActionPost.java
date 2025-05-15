package miage.groupe6.reseausocial.model.entity;

/**
 * Enumération représentant les différentes actions qu’un utilisateur 
 * peut effectuer sur une publication ({@link Post}).
 * <p>
 * Utilisé dans {@link ActionPost}.
 *
 * <ul>
 *     <li>{@code LIKE} : L'utilisateur aime le post.</li>
 *     <li>{@code UNLIKE} : L'utilisateur retire son "like" du post.</li>
 *     <li>{@code REPUBLIER} : L'utilisateur republie (partage) le post.</li>
 * </ul>
 *
 * @see ActionPost
 * @see Post
 */
public enum StatutActionPost {
    /**
     * L'utilisateur aime la publication.
     */
    LIKE,

    /**
     * L'utilisateur retire son "like" (annule le like).
     */
    UNLIKE,

    /**
     * L'utilisateur republie ou partage la publication.
     */
    REPUBLIER
}
