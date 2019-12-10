package org.fhi360.lamis.modules.patient.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.fhi360.lamis.modules.patient.service.util.Scrambler;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "patient")
@Document(indexName = "patient")
@ToString(of = {"id", "surname", "otherNames", "dateBirth"})
public class Patient implements Serializable, Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "HOSPITAL_NUM")
    @Field(type = FieldType.Keyword)
    private String hospitalNum;

    @Size(max = 36)
    @Column(name = "UNIQUE_ID")
    @Field(type = FieldType.Keyword)
    private String uniqueId;

    @Size(max = 45)
    @Column(name = "SURNAME")
    private String surname;

    @Size(max = 75)
    @Column(name = "OTHER_NAMES")
    private String otherNames;

    @Size(max = 7)
    @Column(name = "GENDER")
    @Field(type = FieldType.Keyword)
    private String gender;

    @Column(name = "DATE_BIRTH")
    private LocalDate dateBirth;

    @Size(max = 15)
    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;

    @Size(max = 25)
    @Column(name = "EDUCATION")
    private String education;

    @Size(max = 25)
    @Column(name = "OCCUPATION")
    private String occupation;

    @Size(max = 100)
    @Column(name = "ADDRESS")
    private String address;

    @Size(max = 25)
    @Column(name = "PHONE")
    private String phone;

    @Size(max = 75)
    @Column(name = "NEXT_KIN")
    private String nextKin;

    @Size(max = 100)
    @Column(name = "ADDRESS_KIN")
    private String addressKin;

    @Size(max = 25)
    @Column(name = "PHONE_KIN")
    private String phoneKin;

    @Size(max = 25)
    @Column(name = "RELATION_KIN")
    private String relationKin;

    @Size(max = 15)
    @Column(name = "ENTRY_POINT")
    @Field(type = FieldType.Keyword)
    private String entryPoint;

    @Size(max = 15)
    @Column(name = "TARGET_GROUP")
    private String targetGroup;

    @Column(name = "DATE_CONFIRMED_HIV")
    private LocalDate dateConfirmedHiv;

    @Column(name = "DATE_ENROLLED_PMTCT")
    private LocalDate dateEnrolledPMTCT;

    @Size(max = 100)
    @Column(name = "SOURCE_REFERRAL")
    private String sourceReferral;

    @Size(max = 35)
    @Column(name = "TIME_HIV_DIAGNOSIS")
    private String timeHivDiagnosis;

    @Size(max = 75)
    @Column(name = "TB_STATUS")
    private String tbStatus;

    @Column(name = "PREGNANT")
    private Boolean pregnant;

    @Column(name = "BREASTFEEDING")
    private Boolean breastfeeding;

    @Column(name = "DATE_REGISTRATION")
    private LocalDate dateRegistration;

    @Size(max = 75)
    @Column(name = "STATUS_REGISTRATION")
    private String statusRegistration;

    @Size(max = 15)
    @Column(name = "ENROLLMENT_SETTING")
    private String enrollmentSetting;

    @Column(name = "DATE_STARTED")
    private LocalDate dateStarted;

    @Size(max = 75)
    @Column(name = "CURRENT_STATUS")
    @Field(type = FieldType.Keyword)
    private String currentStatus;

    @Column(name = "DATE_CURRENT_STATUS")
    private LocalDate dateCurrentStatus;

    @Size(max = 100)
    @Column(name = "REGIMENTYPE")
    private String regimentype;

    @Size(max = 100)
    @Column(name = "REGIMEN")
    private String regimen;

    @Size(max = 15)
    @Column(name = "LAST_CLINIC_STAGE")
    private String lastClinicStage;

    @Column(name = "LAST_VIRAL_LOAD")
    private Double lastViralLoad;

    @Column(name = "LAST_CD4")
    private Double lastCd4;

    @Column(name = "LAST_CD4P")
    private Double lastCd4p;

    @Column(name = "DATE_LAST_CD4")
    private LocalDate dateLastCd4;

    @Column(name = "DATE_LAST_VIRAL_LOAD")
    private LocalDate dateLastViralLoad;

    @Column(name = "VIRAL_LOAD_DUE_DATE")
    private LocalDate viralLoadDueDate;

    @Size(max = 15)
    @Column(name = "VIRAL_LOAD_TYPE")
    private String viralLoadType;

    @Column(name = "DATE_LAST_REFILL")
    private LocalDate dateLastRefill;

    @Column(name = "DATE_NEXT_REFILL")
    private LocalDate dateNextRefill;

    @Column(name = "LAST_REFILL_DURATION")
    private Integer lastRefillDuration;

    @Size(max = 15)
    @Column(name = "LAST_REFILL_SETTING")
    private String lastRefillSetting;

    @Column(name = "DATE_LAST_CLINIC")
    private LocalDate dateLastClinic;

    @Column(name = "DATE_NEXT_CLINIC")
    private LocalDate dateNextClinic;

    @Column(name = "DATE_TRACKED")
    private LocalDate dateTracked;

    @Size(max = 75)
    @Column(name = "OUTCOME")
    private String outcome;

    @Size(max = 100)
    @Column(name = "CAUSE_DEATH")
    private String causeDeath;

    @Column(name = "AGREED_DATE")
    private LocalDate agreedDate;

    @Column(name = "SEND_MESSAGE")
    private Boolean sendMessage;

    @Column(name = "TIME_STAMP")
    @JsonIgnore
    private LocalDateTime timestamp;

    @Column(name = "ID_UUID")
    private String uuid;

    @Transient
    @Field(type = FieldType.Keyword)
    private Boolean biometric = false;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Biometrics> biometrics;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @PrePersist
    public void preSave() {
        timestamp = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        timestamp = LocalDateTime.now();
    }

    @PostLoad
    public void unscrammble() {
        surname = Scrambler.unscrambleCharacters(surname);
        otherNames = Scrambler.unscrambleCharacters(otherNames);
        phone = Scrambler.unscrambleCharacters(phone);
        address = Scrambler.unscrambleCharacters(address);
    }
}
