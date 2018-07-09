package io.github.jhipster.gateway.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.gateway.domain.enumeration.Status;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "founded_year", nullable = false)
    private Integer foundedYear;

    @NotNull
    @Size(max = 255)
    @Column(name = "principal", length = 255, nullable = false)
    private String principal;

    @Size(max = 255)
    @Column(name = "vice_principal", length = 255)
    private String vicePrincipal;

    @NotNull
    @Column(name = "student_count", nullable = false)
    private Integer studentCount;

    @NotNull
    @Column(name = "employee_count", nullable = false)
    private Integer employeeCount;

    @Size(max = 1000)
    @Column(name = "address", length = 1000)
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public School name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public School description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public School foundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
        return this;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getPrincipal() {
        return principal;
    }

    public School principal(String principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getVicePrincipal() {
        return vicePrincipal;
    }

    public School vicePrincipal(String vicePrincipal) {
        this.vicePrincipal = vicePrincipal;
        return this;
    }

    public void setVicePrincipal(String vicePrincipal) {
        this.vicePrincipal = vicePrincipal;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public School studentCount(Integer studentCount) {
        this.studentCount = studentCount;
        return this;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public School employeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
        return this;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getAddress() {
        return address;
    }

    public School address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public School status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        School school = (School) o;
        if (school.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), school.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", foundedYear=" + getFoundedYear() +
            ", principal='" + getPrincipal() + "'" +
            ", vicePrincipal='" + getVicePrincipal() + "'" +
            ", studentCount=" + getStudentCount() +
            ", employeeCount=" + getEmployeeCount() +
            ", address='" + getAddress() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
