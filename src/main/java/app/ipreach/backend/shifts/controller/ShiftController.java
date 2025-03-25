package app.ipreach.backend.shifts.controller;

import app.ipreach.backend.shifts.payload.dto.ShiftDto;
import app.ipreach.backend.shifts.service.ShiftService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/shifts")
@RequiredArgsConstructor
@Tag(name = "Shifts", description = "Shift methods to create, list and filter")
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping
    public ResponseEntity<?> listShifts(
        @RequestParam long locationId,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM") YearMonth month,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
        @RequestParam(required = false) boolean detailed
    ) {

        return shiftService.listShifts(locationId, month, date, detailed);
    }

    @GetMapping("/{shiftId}")
    public ResponseEntity<?> getShift(@PathVariable Integer shiftId) {
        return shiftService.getShift(shiftId);
    }

    @PostMapping()
    public ResponseEntity<?> createShiftList(@RequestBody List<ShiftDto> shiftsDto) {
        return shiftService.createShiftList(shiftsDto);
    }
}
