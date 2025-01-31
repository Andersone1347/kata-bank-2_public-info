package com.bank.profile.dto;

import com.bank.profile.entity.PassportEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ДТО для сущности {@link PassportEntity}
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto implements Serializable {
    private Long id;

    private Integer series;

    private Long number;

    private String lastName;

    private String firstName;

    private String middleName;

    private String gender;

    private LocalDate birthDate;

    private String birthPlace;

    private String issuedBy;

    private LocalDate dateOfIssue;

    private Integer divisionCode;

    private LocalDate expirationDate;

    private RegistrationDto registration;
}
