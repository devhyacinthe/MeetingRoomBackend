package com.devhyacinthe.MeetingRoomBackend.service;

import com.devhyacinthe.MeetingRoomBackend.dto.ReservationReportDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Reservation;
import com.devhyacinthe.MeetingRoomBackend.repository.ReservationRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReservationRepository reservationRepository;

    public ReportService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public String generateReservationReport(Long roomId, LocalDate dateReservation) throws JRException {

        // 1️⃣ Récupérer les réservations filtrées
        List<Reservation> reservations;

        if (roomId != null && dateReservation != null) {
            reservations = reservationRepository.findByRoomIdAndDateReservation(roomId, dateReservation);
        } else if (roomId != null) {
            reservations = reservationRepository.findByRoomId(roomId);
        } else if (dateReservation != null) {
            reservations = reservationRepository.findByDateReservation(dateReservation);
        } else {
            reservations = reservationRepository.findAll();
        }

        // 2️⃣ Transformer en DTO pour JasperReports
        List<ReservationReportDTO> reservationDTOs = reservations.stream()
                .map(r -> new ReservationReportDTO(
                        r.getReservationNum(),
                        r.getRoom().getName(),
                        r.getUser().getUsername(),
                        r.getStatus(),
                        r.getDateReservation(),
                        r.getStartTime(),
                        r.getEndTime()
                ))
                .collect(Collectors.toList());

        // 3️⃣ Charger le fichier JRXML
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/report.jrxml");

        // 4️⃣ Créer la datasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reservationDTOs);

        // 5️⃣ Paramètres du rapport
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Rapport des Réservations de Salles");

        // 6️⃣ Remplir le rapport
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // 7️⃣ Exporter en PDF
        String pdfFilePath = "reservation_report.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);

        return new File(pdfFilePath).getAbsolutePath();
    }
}
