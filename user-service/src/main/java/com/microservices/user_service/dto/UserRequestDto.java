package com.microservices.user_service.dto;


import com.microservices.user_service.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UserRequestDto {

    @NotBlank(message = "Le nom complet est requis")
    private String fullName;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est requis")
    private String email;

    @NotBlank(message = "Le numéro de téléphone est requis")
    private String phoneNumber;

    @NotNull(message = "Le rôle est requis")
    private UserRole role;

    // Champs spécifiques aux prestataires
    private String speciality;
    private String location;
    private BigDecimal hourlyRate;
    private String bio;

    // Constructeurs
    public UserRequestDto() {
    }

    public UserRequestDto(String fullName, String email, String phoneNumber, UserRole role) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters et Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
