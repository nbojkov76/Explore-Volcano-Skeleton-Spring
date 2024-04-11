package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanoSeedRootDto {

    @XmlElement(name = "volcanologist")
    List<VolcanoLogistSeedDto> volcanos;

    public List<VolcanoLogistSeedDto> getVolcanos() {
        return volcanos;
    }

    public void setVolcanos(List<VolcanoLogistSeedDto> volcanos) {
        this.volcanos = volcanos;
    }
}
