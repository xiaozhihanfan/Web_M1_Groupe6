package miage.groupe6.reseausocial.model.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.Groupe;
import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;

/**
 * Repository JPA pour l'entité {@link Message}.
 * <p>
 * Permet d'effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les messages (privés ou de groupe) dans la base de données.
 * </p>
 * 
 * Cette interface hérite de {@link JpaRepository} et utilise {@link Long}
 * comme type d'identifiant du message.
 * 
 */
public interface MessageRepository extends JpaRepository<Message, Long>{
    /**
     * Récupère tous les messages échangés entre deux utilisateurs, dans les deux sens,
     * triés par date croissante.
     *
     * @param u1 premier utilisateur
     * @param u2 second utilisateur
     * @param u3 même que u1 (inversé)
     * @param u4 même que u2 (inversé)
     * @return liste des messages échangés entre les deux utilisateurs
     */
    List<Message> findByEnvoyeurAndRecepteurOrRecepteurAndEnvoyeurOrderByTempsAsc(Utilisateur u1, Utilisateur u2, Utilisateur u3, Utilisateur u4);
    
    /**
     * Récupère tous les messages associés à un groupe donné,
     * triés par ordre chronologique croissant.
     *
     * @param groupe le groupe concerné
     * @return liste des messages du groupe
     */
    List<Message> findByGroupeOrderByTempsAsc(Groupe groupe);
}