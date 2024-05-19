package com.supportsystem.application.domains;

import com.supportsystem.application.shared.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicketComment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ticket_id", insertable = false)
	private Long ticketId;

	@Column(name = "created_on", insertable = false)
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "last_modified", insertable = false)
	private Date lastModified;

	private String description;

}
