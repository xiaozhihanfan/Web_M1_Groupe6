package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.GroupeMembre;
import miage.groupe6.reseausocial.model.entity.GroupeMembreId;
import miage.groupe6.reseausocial.model.entity.MembreRole;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link GroupeMembre}.
 * <p>
 * Permet d'effectuer des opérations CRUD (Create, Read, Update, Delete) 
 * sur les relations membres-groupe dans la base de données.
 * </p>
 * 
 * Cette interface hérite de {@link JpaRepository} et utilise {@link GroupeMembreId}
 * comme clé primaire composite.
 * 
 */
public interface GroupeMembreRepository extends JpaRepository<GroupeMembre, GroupeMembreId>{
    /**
     * Récupère toutes les inscriptions de groupes pour un utilisateur donné
     * et un rôle précis (ADMIN ou MEMBRE).
     *
     * @param utilisateur l’utilisateur concerné
     * @param role        le rôle dans le groupe
     * @return la liste des inscriptions correspondantes
     */
    List<GroupeMembre> findByUtilisateurAndRole(Utilisateur utilisateur, MembreRole role);

    List<GroupeMembre> findByUtilisateur(Utilisateur utilisateur);
}
