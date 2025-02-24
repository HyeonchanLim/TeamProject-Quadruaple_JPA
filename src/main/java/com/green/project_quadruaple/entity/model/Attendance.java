package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attendance {

    @Id
    @Column(name = "ticket_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticketId;

    @Column(name = "strf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest strfId;

    @Column(nullable = false)
    private int attendance;

    public Attendance(Ticket ticketId, StayTourRestaurFest strfId, int attendance) {
        this.ticketId = ticketId;
        this.strfId = strfId;
        this.attendance = attendance;
    }
}
