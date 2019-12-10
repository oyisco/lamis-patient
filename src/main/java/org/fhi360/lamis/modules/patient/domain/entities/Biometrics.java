package org.fhi360.lamis.modules.patient.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "biometrics")
public class Biometrics implements Serializable {
    @Id
    @Column(name = "BIOMETRIC_ID")
    private String id;

    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "ID_UUID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Patient patient;

    @JoinColumn(name = "FACILITY_ID", referencedColumnName = "FACILITY_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Facility facility;

    @Column(name = "HOSPITAL_NUM")
    private String hospitalNumber;

    @Lob
    private byte[] template;

    @Column(name = "BIOMETRIC_TYPE")
    private String biometricType;

    @Column(name = "TEMPLATE_TYPE")
    private String templateType;

    @Column(name = "PATIENT_NAME")
    private String name;

    @Column(name = "PATIENT_ADDRESS")
    private String address;

    @Column(name = "PATIENT_PHONE")
    private String phone;

    @Column(name = "PATIENT_GENDER")
    private String gender;

    @Column(name = "ENROLLMENT_DATE")
    private LocalDate date;

    @Column(name = "TIME_STAMP")
    @JsonIgnore
    private LocalDateTime timestamp;

    @Column(name = "UPLOADED")
    @JsonIgnore
    private Boolean uploaded;

    @Column(name = "TIME_UPLOADED")
    @JsonIgnore
    private LocalDateTime timeUploaded;

    @PrePersist
    public void preSave() {
        timestamp = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        timestamp = LocalDateTime.now();
    }
}
