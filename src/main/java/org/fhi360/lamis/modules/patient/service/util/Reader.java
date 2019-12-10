package org.fhi360.lamis.modules.patient.service.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reader {
    private String name;
    private String vendor;
    private String serial;
}
