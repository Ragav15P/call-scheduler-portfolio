package in.ragav.personalportfolio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.ragav.personalportfolio.model.TimeSlot;

@Service
public class TimeSlotService 
{
	public List<TimeSlot> generateDailySlots(LocalDate date) {
	    List<TimeSlot> slots = new ArrayList<>();
	    
	    // Morning slots 10:00 to 12:00
	    LocalTime morningStart = LocalTime.of(10, 0);
	    LocalTime morningEnd = LocalTime.of(12, 0);

	    // Evening slots 16:00 (4 PM) to 22:00 (10 PM)
	    LocalTime eveningStart = LocalTime.of(16, 0);
	    LocalTime eveningEnd = LocalTime.of(23, 0);

	    // Helper to generate slots for a given start/end time
	    slots.addAll(generateSlotsForRange(date, morningStart, morningEnd));
	    slots.addAll(generateSlotsForRange(date, eveningStart, eveningEnd));
	    
	    return slots;
	}

	private List<TimeSlot> generateSlotsForRange(LocalDate date, LocalTime start, LocalTime end) {
	    List<TimeSlot> slots = new ArrayList<>();
	    LocalDateTime slotStart = LocalDateTime.of(date, start);

	    while (slotStart.toLocalTime().isBefore(end)) {
	        LocalDateTime slotEnd = slotStart.plusMinutes(30);
	        TimeSlot slot = new TimeSlot();
	        slot.setStartTime(slotStart);
	        slot.setEndTime(slotEnd);
	        slot.setBooked(false);
	        slots.add(slot);
	        slotStart = slotEnd;
	    }

	    return slots;
}
}