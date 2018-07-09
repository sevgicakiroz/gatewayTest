package io.github.jhipster.gateway.repository;

import io.github.jhipster.gateway.domain.School;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the School entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
