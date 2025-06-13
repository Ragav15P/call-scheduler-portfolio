package in.ragav.personalportfolio.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ragav.personalportfolio.model.TimeSlot;

public interface TimeSlotInterface extends JpaRepository<TimeSlot,Long>{
	List<TimeSlot> findByIsBookedFalse();
	List<TimeSlot> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
	List<TimeSlot> findByStartTimeBetweenAndIsBookedFalse(LocalDateTime start, LocalDateTime end);

}
