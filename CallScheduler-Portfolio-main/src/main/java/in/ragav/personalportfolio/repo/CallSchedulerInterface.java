package in.ragav.personalportfolio.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import in.ragav.personalportfolio.model.CallScheduler;

public interface CallSchedulerInterface extends JpaRepository<CallScheduler, Long> {
    // You can add custom query methods here if needed
}
