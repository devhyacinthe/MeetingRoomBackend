package com.devhyacinthe.MeetingRoomBackend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Réservations")
@RequestMapping("/api/reservations")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController {

}


//POST /reservations → créer une réservation (UTILISATEUR)

//GET /reservations → voir mes réservations (UTILISATEUR)

//GET /admin/reservations → voir toutes les réservations (ADMIN)

//PUT /admin/reservations/{id}/confirmer → confirmer une réservation (ADMIN)

//PUT /admin/reservations/{id}/annuler → annuler une réservation (ADMIN)

//GET /admin/reservations/export/pdf → exporter les réservations en PDF

/*
Quand un utilisateur tente de réserver une salle :

Vérifier si la salle est déjà réservée à la même date et pour une plage horaire qui chevauche celle demandée.

Si oui → rejeter la réservation avec un message clair.

        Sinon → sauvegarder en EN_ATTENTE.


Lorsqu’une réservation est confirmée → envoi d’un email à l’utilisateur :
        "Votre réservation de la salle X le JJ/MM/AAAA de HH:MM à HH:MM est confirmée."

Lorsqu’une réservation est annulée → autre email avec le motif. */