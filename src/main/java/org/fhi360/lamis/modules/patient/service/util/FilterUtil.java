package org.fhi360.lamis.modules.patient.service.util;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.fhi360.lamis.modules.base.config.ContextProvider;
import org.fhi360.lamis.modules.base.domain.entities.Authority;
import org.fhi360.lamis.modules.base.domain.entities.User;
import org.fhi360.lamis.modules.base.domain.repositories.UserRepository;
import org.fhi360.lamis.modules.patient.domain.entities.LamisUser;
import org.fhi360.lamis.modules.security.config.security.SecurityUtils;

import java.util.Optional;
import java.util.Set;

public class FilterUtil {

    public static BoolQueryBuilder getFacilityFilterForCurrentUser() {
        final BoolQueryBuilder[] filter = {QueryBuilders.boolQuery().filter(QueryBuilders.existsQuery("surname"))};
        UserRepository userRepository = ContextProvider.getBean(UserRepository.class);
        /*
        SecurityUtils.getCurrentUserLogin().map()
        Optional<User> user = userRepository.findOneWithAuthoritiesByLogin(login);
        user.ifPresent(u -> {
            Set<Authority> authorities = u.getAuthorities();
            boolean dec = authorities.stream()
                    .anyMatch(authority -> authority.getName().contains("ROLE_DEC"));
            if (dec) {
                if (u instanceof LamisUser) {
                    filter[0] = filter[0].filter(QueryBuilders.termQuery("facility.id",
                            ((LamisUser) u).getFacility().getId()));
                }
            }
        });
         */
        return filter[0];
    }
}
