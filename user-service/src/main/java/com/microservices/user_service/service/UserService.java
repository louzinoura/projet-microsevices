package com.microservices.user_service.service;


import com.microservices.user_service.dto.UserRequestDto;
import com.microservices.user_service.dto.UserResponseDto;
import com.microservices.user_service.entity.User;
import com.microservices.user_service.entity.UserRole;
import com.microservices.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Validation des champs obligatoires pour les prestataires
        if (userRequestDto.getRole() == UserRole.PROVIDER) {
            validateProviderFields(userRequestDto);
        }

        // Créer une nouvelle entité User
        User user = convertToEntity(userRequestDto);

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Retourner le DTO de réponse
        return convertToResponseDto(savedUser);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
        return convertToResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));

        // Vérifier si l'email n'est pas déjà utilisé par un autre utilisateur
        if (!existingUser.getEmail().equals(userRequestDto.getEmail()) &&
                userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Validation des champs obligatoires pour les prestataires
        if (userRequestDto.getRole() == UserRole.PROVIDER) {
            validateProviderFields(userRequestDto);
        }

        // Mettre à jour les champs
        updateUserFields(existingUser, userRequestDto);

        // Sauvegarder les modifications
        User updatedUser = userRepository.save(existingUser);

        return convertToResponseDto(updatedUser);
    }

    private void validateProviderFields(UserRequestDto userRequestDto) {
        if (userRequestDto.getSpeciality() == null || userRequestDto.getSpeciality().trim().isEmpty()) {
            throw new RuntimeException("La spécialité est requise pour les prestataires");
        }
        if (userRequestDto.getLocation() == null || userRequestDto.getLocation().trim().isEmpty()) {
            throw new RuntimeException("La localisation est requise pour les prestataires");
        }
        if (userRequestDto.getHourlyRate() == null || userRequestDto.getHourlyRate().doubleValue() <= 0) {
            throw new RuntimeException("Le tarif horaire doit être supérieur à 0 pour les prestataires");
        }
        if (userRequestDto.getBio() == null || userRequestDto.getBio().trim().isEmpty()) {
            throw new RuntimeException("La bio est requise pour les prestataires");
        }
    }

    private User convertToEntity(UserRequestDto dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());

        // Ajouter les champs spécifiques aux prestataires si nécessaire
        if (dto.getRole() == UserRole.PROVIDER) {
            user.setSpeciality(dto.getSpeciality());
            user.setLocation(dto.getLocation());
            user.setHourlyRate(dto.getHourlyRate());
            user.setBio(dto.getBio());
        }

        return user;
    }

    private void updateUserFields(User user, UserRequestDto dto) {
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());

        // Mettre à jour ou nettoyer les champs spécifiques aux prestataires
        if (dto.getRole() == UserRole.PROVIDER) {
            user.setSpeciality(dto.getSpeciality());
            user.setLocation(dto.getLocation());
            user.setHourlyRate(dto.getHourlyRate());
            user.setBio(dto.getBio());
        } else {
            // Nettoyer les champs prestataire si l'utilisateur devient client
            user.setSpeciality(null);
            user.setLocation(null);
            user.setHourlyRate(null);
            user.setBio(null);
        }
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());

        // Ajouter les champs spécifiques aux prestataires si l'utilisateur est un prestataire
        if (user.getRole() == UserRole.PROVIDER) {
            dto.setSpeciality(user.getSpeciality());
            dto.setLocation(user.getLocation());
            dto.setHourlyRate(user.getHourlyRate());
            dto.setBio(user.getBio());
        }

        return dto;
    }
}