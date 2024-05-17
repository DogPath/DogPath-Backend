package dogpath.server.dogpath.domain.user.repository;

import dogpath.server.dogpath.domain.user.domain.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
