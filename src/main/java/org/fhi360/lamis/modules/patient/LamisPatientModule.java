package org.fhi360.lamis.modules.patient;

import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.annotations.AcrossDepends;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;

@AcrossDepends(required = AcrossHibernateJpaModule.NAME)
public class LamisPatientModule extends AcrossModule {
    public static final String NAME = "LAMISPatientModule";

    public LamisPatientModule() {
        super();
        addApplicationContextConfigurer(
                new ComponentScanConfigurer(getClass().getPackage().getName() + ".service"));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
