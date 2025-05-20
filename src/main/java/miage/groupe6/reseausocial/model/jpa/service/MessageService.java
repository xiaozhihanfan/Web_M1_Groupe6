package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;

/**
 * Service de gestion des messages privés entre utilisateurs.
 * Fournit des fonctionnalités pour envoyer et récupérer des messages.
 */
@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Récupère tous les messages échangés entre deux utilisateurs,
     * triés par ordre chronologique (ascendant).
     *
     * @param u1 Premier utilisateur impliqué dans la conversation
     * @param u2 Deuxième utilisateur impliqué dans la conversation
     * @return Liste des messages échangés entre u1 et u2
     */
    public List<Message> getMessageEntre(Utilisateur u1, Utilisateur u2) {
        return messageRepository.findByEnvoyeurAndRecepteurOrRecepteurAndEnvoyeurOrderByTempsAsc(u1, u2, u1, u2);
    }

    /**
     * Envoie un nouveau message d'un utilisateur à un autre.
     *
     * @param envoyeur Utilisateur qui envoie le message
     * @param recepteur Utilisateur qui reçoit le message
     * @param contenu Contenu textuel du message
     * @return Le message nouvellement créé et sauvegardé
     */
    public Message envoyerMessage(Utilisateur envoyeur, Utilisateur recepteur, String contenu) {
        Message messages = new Message();
        messages.setEnvoyeur(envoyeur);
        messages.setRecepteur(recepteur);
        messages.setText(contenu);
        messages.setTemps(new Date());

        return messageRepository.save(messages);
    }
}