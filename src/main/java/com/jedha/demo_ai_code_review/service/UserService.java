package com.jedha.demo_ai_code_review.service;


import com.jedha.demo_ai_code_review.model.User;
import com.jedha.demo_ai_code_review.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));
    }

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        return userRepository.save(user);
    }

    public User update(Long id, User userDetails) {
        User user = findById(id);

        if (!user.getEmail().equals(userDetails.getEmail())
                && userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        user.setNom(userDetails.getNom());
        user.setPrenom(userDetails.getPrenom());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}