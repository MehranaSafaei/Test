package org.example.mehrana.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Leave")
@NamedQueries({@NamedQuery(name = "CountExistsByPersonnelId",query = "SELECT COUNT(l) FROM Leave l WHERE l.personnel.id = :personnelId")})
public class Leave extends AbstractLeave{
}
