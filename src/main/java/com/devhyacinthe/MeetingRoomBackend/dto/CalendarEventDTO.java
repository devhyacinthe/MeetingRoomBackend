package com.devhyacinthe.MeetingRoomBackend.dto;

import java.util.Objects;


public class CalendarEventDTO {
    private String title;      // Nom à afficher dans le calendrier
    private String start;      // Date/heure ISO-8601
    private String end;        // Date/heure ISO-8601
    private String color;      // Optionnel : couleur (par statut)

    public CalendarEventDTO() {

    }

    public CalendarEventDTO(String title, String start, String end, String color) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEventDTO that = (CalendarEventDTO) o;
        return Objects.equals(title, that.title) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, end, color);
    }
}
