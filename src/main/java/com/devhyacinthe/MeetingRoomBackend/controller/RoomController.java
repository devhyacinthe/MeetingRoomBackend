package com.devhyacinthe.MeetingRoomBackend.controller;


import com.devhyacinthe.MeetingRoomBackend.dto.GetRoomDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.RoomDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import com.devhyacinthe.MeetingRoomBackend.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Salles")
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/admin/rooms")
    @Operation(summary = "Créer une salle (ADMIN seulement)")
    public Room createRoom(@Valid @RequestBody RoomDTO request)  {
        return roomService.createRoom(request);
    }

    @GetMapping("/rooms")
    @Operation(summary = "Lister toutes les salles")
    public List<GetRoomDTO> getAllRooms()  {
        return roomService.getAllRooms();
    }

    @GetMapping("/rooms/{id}")
    @Operation(summary = "Voir une salle avec son ID")
    public Room getOneRoomById(@PathVariable Long id)  {
        return roomService.getOneRoomById(id);
    }

    @PatchMapping("/admin/rooms/{id}")
    @Operation(summary = "Modifier une salle (ADMIN)")
    public Room updateOneRoomById(@PathVariable Long id, @RequestBody RoomDTO request)  {
        return roomService.updateOneRoomById(id, request);
    }

    @DeleteMapping("/admin/rooms/{id}")
    @Operation(summary = "Supprimer une salle (ADMIN)")
    public String deleteOneRoomById(@PathVariable Long id)  {
        roomService.deleteOneRoomById(id);
        return String.format("l'utilisateur avec l'ID: %d a été bien supprimé", id);
    }
}
