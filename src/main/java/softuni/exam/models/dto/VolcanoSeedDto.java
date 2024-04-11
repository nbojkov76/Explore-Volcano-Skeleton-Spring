package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Country;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class VolcanoSeedDto {
@Expose
    private String name;
    @Expose
    private int elevation;
    @Expose
    private String volcanoType;
    @Expose
    private String isActive;
    @Expose
    private String lastEruption;
    @Expose
    private long country;

    public VolcanoSeedDto() {
    }
@Size(min = 2,max = 30)
@NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
@Positive
@NotNull
    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(String volcanoType) {
        this.volcanoType = volcanoType;
    }
@NotNull
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }
@NotNull
    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
