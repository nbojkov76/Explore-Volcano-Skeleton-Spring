package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "volcanologists")
public class Volcanologist  extends  BaseEntity{

    private String firstName;

    private String lastName;

    private Double salary;

    private Integer age;

    private LocalDate exploringFrom;

    private Volcano exploringVolcano;


    public Volcanologist() {
    }
@Column(name = "first_name", unique = true,nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name", unique = true,nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
@Column(nullable = false)
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
@Column(nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
@Column(name = "exploring_from")
    public LocalDate getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(LocalDate exploringFrom) {
        this.exploringFrom = exploringFrom;
    }
@ManyToOne
    public Volcano getExploringVolcano() {
        return exploringVolcano;
    }

    public void setExploringVolcano(Volcano exploringVolcano) {
        this.exploringVolcano = exploringVolcano;
    }
}
