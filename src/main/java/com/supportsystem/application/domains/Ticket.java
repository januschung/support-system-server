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

	@Column(name = "created_on", insertable = false)
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "last_modified", insertable = false)
	private Date lastModified;

	@Column(name = "assignee_id", insertable=false, updatable=false)
	private Long assigneeId;

	@Column(name = "client_id")
	private Long clientId;

	private String description;

	@Enumerated(EnumType.STRING)
	private Status.Ticket status;

	@Enumerated(EnumType.STRING)
	private Status.Resolution resolution;

	@OneToMany(targetEntity=Ticket.class,cascade = CascadeType.ALL,
		fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "ticketId")
	private List<TicketComment> ticketComments;

}
