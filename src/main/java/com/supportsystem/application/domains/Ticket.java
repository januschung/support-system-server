package com.supportsystem.application.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.supportsystem.application.shared.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "modified_by", nullable = false)
    private Long modifiedBy;

    @Column(name = "last_modified", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Column(name = "assignee_id", nullable = false, insertable = false, updatable = false)
    private Long assigneeId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status.Ticket status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status.Resolution resolution;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private List<TicketComment> ticketComments;

}
