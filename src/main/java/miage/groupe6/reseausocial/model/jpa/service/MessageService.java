package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miage.groupe6.reseausocial.model.entity.Message;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessageEntre(Utilisateur u1, Utilisateur u2) {
        return messageRepository.findByEnvoyeurAndRecepteurOrRecepteurAndEnvoyeurOrderByTempsAsc(u1, u2, u1, u2);
    }

    public Message envoyerMessage(Utilisateur envoyeur, Utilisateur recepteur, String contenu) {
        Message messages = new Message();
        messages.setEnvoyeur(envoyeur);
        messages.setRecepteur(recepteur);
        messages.setText(contenu);
        messages.setTemps(new Date());

        return messageRepository.save(messages);
    }
}