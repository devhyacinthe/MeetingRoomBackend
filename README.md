## 🚀 Projet : Système de gestion de réservations de salles (Meeting Room Booking System)

Un système pour gérer la réservation de salles (de réunion, de classe, ou d’événements).

📍Fonctionnalités principales

1- Gestion des utilisateurs (Spring Security + JWT)

- Rôles : ADMIN (gère toutes les salles), UTILISATEUR (réserve des salles).

- Authentification JWT.

2- Gestion des salles

- CRUD salles (nom, capacité, équipements, disponibilité).

- Un admin peut définir les créneaux disponibles.

3- Réservations

- Un utilisateur peut réserver une salle pour une date et une plage horaire.

- Vérification automatique des conflits de réservation.

- Statuts : EN_ATTENTE, CONFIRMÉE, ANNULÉE.

4- Notifications

- Envoi d’email ou notification quand une réservation est confirmée ou annulée.

5- Historique et rapports

- Un utilisateur peut consulter son historique de réservations.

- Un admin peut générer un rapport (PDF/Excel) des réservations par salle.