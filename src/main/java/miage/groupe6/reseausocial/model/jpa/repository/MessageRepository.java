package miage.groupe6.reseausocial.model.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import miage.groupe6.reseausocial.model.entity.Message;

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

}
