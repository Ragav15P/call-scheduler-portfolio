package in.ragav.personalportfolio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.ragav.personalportfolio.model.TimeSlot;
import in.ragav.personalportfolio.repo.TimeSlotInterface;
import in.ragav.personalportfolio.service.TimeSlotService;
@Controller
public class TimeSlotController 
{
	
	@Autowired
	private TimeSlotInterface timeSlotRepository;
	@Autowired
	private TimeSlotService timeSlotService;
	@GetMapping("/available-slots")
	@ResponseBody
	public List<TimeSlot> getAvailableSlots() {
	    LocalDate today = LocalDate.now();
	    
	    // If slots for today are not yet generated, generate them
	    if (timeSlotRepository.findByStartTimeBetween(
	            today.atStartOfDay(), today.plusDays(1).atStartOfDay()).isEmpty()) {
	        List<TimeSlot> slots = timeSlotService.generateDailySlots(today);
	        timeSlotRepository.saveAll(slots);
	    }

	    // Return only unbooked slots
	    return timeSlotRepository.findByStartTimeBetweenAndIsBookedFalse(
	        today.atStartOfDay(), today.plusDays(1).atStartOfDay()
	    );
	}

}
