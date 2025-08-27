package com.devhyacinthe.MeetingRoomBackend.dto;

import com.devhyacinthe.MeetingRoomBackend.enums.RoomStatus;
import jakarta.validation.constraints.*;

import java.util.List;

public class RoomDTO {

    private Long id;

    @NotNull(message = "Entrez le nom de la salle")
    private String name;

    @NotNull(message = "Les équipements sont obligatoires")
    @Size(min = 1, message = "Il doit y avoir au moins un équipement")
    private List<@NotBlank String> equipments;

    private String location;

    @NotNull(message = "La salle doit avoir une capacité")
    @Positive
    private Integer capacity;

    private RoomStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
