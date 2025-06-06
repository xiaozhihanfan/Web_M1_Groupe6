package miage.groupe6.reseausocial.model.jpa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import miage.groupe6.reseausocial.model.entity.Utilisateur;
import miage.groupe6.reseausocial.model.jpa.repository.UtilisateurRepository;

/**
 * Service métier pour la gestion des utilisateurs.
 * Contient les opérations liées à la consultation et à la mise à jour des profils utilisateurs.
 */
@Service
public class SettingsService {

    @Autowired
    private UtilisateurRepository ur;

    /**
     * Récupère un utilisateur par son identifiant unique.
     *
     * @param id l'identifiant de l'utilisateur
     * @return un Optional contenant l'utilisateur s'il existe, sinon vide
     */
    public Optional<Utilisateur> getUtilisateurById(Long id){
        return ur.findById(id);
    }

    /**
     * Met à jour les informations du profil utilisateur.
     *
     * @param id       l'identifiant de l'utilisateur à modifier
     * @param newData  l'objet contenant les nouvelles données (nom, email, avatar, etc.)
     * @return l'utilisateur mis à jour
     * @throws RuntimeException si aucun utilisateur n'est trouvé avec cet identifiant
     */
    public Utilisateur updateUtilisateur(Long id, Utilisateur newData) {
        return ur.findById(id).map(utilisateur -> {
            utilisateur.setNomU(newData.getNomU());
            utilisateur.setPrenomU(newData.getPrenomU());
            utilisateur.setEmailU(newData.getEmailU());
            utilisateur.setDescriptionU(newData.getDescriptionU());
            utilisateur.setBirthday(newData.getBirthday());
            utilisateur.setTelephone(newData.getTelephone());
            utilisateur.setIne(newData.getIne());
            utilisateur.setUniversite(newData.getUniversite());
            utilisateur.setVille(newData.getVille());
            return ur.save(utilisateur);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec id: " + id));
    }

    /**
     * Met à jour le mot de passe de l'utilisateur après vérification.
     *
     * @param id              l'identifiant de l'utilisateur
     * @param currentPassword le mot de passe actuel saisi par l'utilisateur
     * @param newPassword     le nouveau mot de passe à enregistrer
     * @throws RuntimeException si l'utilisateur est introuvable ou si le mot de passe actuel est incorrect
     */
    public void updatePassword(Long id, String currentPassword, String newPassword) {
        Utilisateur utilisateur = ur.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec id: " + id));

        if (!utilisateur.getMpU().equals(currentPassword)) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }

        utilisateur.setMpU(newPassword);
        ur.save(utilisateur);
    }

    /**
     * Met à jour uniquement l’URL de l’avatar de l’utilisateur.
     * <p>
     * Utilise une transaction pour garantir la cohérence de l’opération.
     * </p>
     *
     * @param id        identifiant de l’utilisateur
     * @param avatarUrl nouvelle URL Base64 ou chemin de l’avatar
     * @throws RuntimeException si l’utilisateur n’est pas trouvé
     */
    @Transactional
    public void updateAvatarUrl(Long id, String avatarUrl) {
        Utilisateur utilisateur = ur.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec id: " + id));
        utilisateur.setAvatarU(avatarUrl);
        ur.save(utilisateur);
    }

}