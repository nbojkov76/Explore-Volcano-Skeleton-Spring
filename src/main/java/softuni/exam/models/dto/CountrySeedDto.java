package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CountrySeedDto {
@Expose
    private String name;
@Expose
    private String capital;

    public CountrySeedDto() {
    }
@Size(min =  3 ,max = 30)
@NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
@Size(min = 3,max = 30)
    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
