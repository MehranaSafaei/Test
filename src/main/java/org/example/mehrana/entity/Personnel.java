package org.example.mehrana.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Personnel")
@NamedQueries({
        @NamedQuery(name = "CountByNationalCode", query = "SELECT COUNT(p) FROM Personnel p WHERE p.nationalCode = :nationalCode"),
        @NamedQuery(name = "SelectByNationalCode", query = "SELECT p FROM Personnel p WHERE p.nationalCode = :nationalCode"),
        @NamedQuery(name = "SelectAll", query = "SELECT p FROM Personnel p"),
        @NamedQuery(name = "selectById",query = "SELECT p FROM Personnel p WHERE p.id = :id"),
        @NamedQuery(name = "selectByName", query = "SELECT p FROM Personnel p WHERE p.username = :username")
})
public class Personnel extends AbstractPersonnel{

}
