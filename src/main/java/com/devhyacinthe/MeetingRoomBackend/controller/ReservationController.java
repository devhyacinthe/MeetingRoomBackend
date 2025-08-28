package com.devhyacinthe.MeetingRoomBackend.controller;

import com.devhyacinthe.MeetingRoomBackend.dto.GetReservationDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.ReservationDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Reservation;
import com.devhyacinthe.MeetingRoomBackend.service.ReportService;
import com.devhyacinthe.MeetingRoomBackend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Réservations")
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReportService reportService;

    public ReservationController(ReservationService reservationService, ReportService reportService) {
        this.reservationService = reservationService;
        this.reportService = reportService;
    }

    @PostMapping("/reservations")
    @Operation(summary = "Créer une réservation (UTILISATEUR)")
    public Reservation createReservation(@RequestBody ReservationDTO request) {
        return reservationService.createReservation(request);
    }

    @GetMapping("/reservations")
    @Operation(summary = "Voir mes réservations (UTILISATEUR)")
    public List<GetReservationDTO> getAllUserReservations() {
        return reservationService.getAllUserReservations();
    }

    @GetMapping("/admin/reservations")
    @Operation(summary = "Voir toutes les réservations (ADMIN)")
    public List<GetReservationDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PatchMapping("/admin/reservations/{id}/confirmer")
    @Operation(summary = "Confirmer une réservation (ADMIN)")
    public Reservation confirmReservationById(@PathVariable Long id)  {
        return reservationService.confirmReservationById(id);
    }

    @PatchMapping("/admin/reservations/{id}/annuler")
    @Operation(summary = "Annuler une réservation (ADMIN)")
    public Reservation cancelReservationById(@PathVariable Long id)  {
        return reservationService.cancelReservationById(id);
    }

    @GetMapping("/admin/reservations/export/pdf")
    @Operation(summary = "Exporter les réservations en PDF")
    public String generateReport() throws JRException {
        return reportService.generateReservationReport();
    }

}