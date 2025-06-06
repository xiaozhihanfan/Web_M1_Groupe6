package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Groupe;
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


    // --------------- us 4.1 chat privé ----------------------------

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

    // --------------- us 4.2 chat de gourpe ----------------------------

    /**
     * Récupère les messages envoyés dans un groupe donné,
     * triés par date croissante.
     *
     * @param groupe le groupe cible
     * @return liste des messages associés au groupe
     */
    public List<Message> getMessagesGroupe(Groupe groupe) {
        return messageRepository.findByGroupeOrderByTempsAsc(groupe);
    }

    /**
     * Envoie un message textuel dans un groupe spécifique.
     *
     * @param message contenu du message
     * @param utilisateur utilisateur qui envoie le message
     * @param groupe groupe destinataire du message
     * @return le message créé et enregistré
     */
    public Message envoyerMessageGroupe(String message, Utilisateur utilisateur, Groupe groupe) {
        Message messages = new Message();
        messages.setEnvoyeur(utilisateur);
        messages.setGroupe(groupe);
        messages.setTemps(new Date());
        messages.setText(message);

        return messageRepository.save(messages);
    }
}