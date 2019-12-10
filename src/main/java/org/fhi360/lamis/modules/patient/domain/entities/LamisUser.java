package org.fhi360.lamis.modules.patient.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fhi360.lamis.modules.base.domain.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@DiscriminatorValue("LAMIS")
public class LamisUser extends User {
    @ManyToOne
    @NotNull
    @JoinColumn(name = "facility_id")
    Facility facility;
}
