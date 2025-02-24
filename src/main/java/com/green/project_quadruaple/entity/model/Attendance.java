package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @JoinColumn(name = "ticket_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticketId;

    @JoinColumn(name = "strf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest strfId;

    @Column(nullable = false)
    private int attendance;

}
