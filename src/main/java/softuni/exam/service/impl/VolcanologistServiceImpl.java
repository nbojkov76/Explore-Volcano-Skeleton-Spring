package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoLogistSeedDto;
import softuni.exam.models.dto.VolcanoSeedRootDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private final VolcanologistRepository volcanologistRepository;

    private final XmlParser xmlParser;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final VolcanoRepository volcanoRepository;


    private static final String VOLCANO_LOGIST_FILE = "src/main/resources/files/xml/volcanologists.xml";

    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, VolcanoRepository volcanoRepository) {
        this.volcanologistRepository = volcanologistRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(VOLCANO_LOGIST_FILE));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        VolcanoSeedRootDto volcanoSeedRootDto =
                xmlParser.fromFile(VOLCANO_LOGIST_FILE, VolcanoSeedRootDto.class);

        for (VolcanoLogistSeedDto volcano : volcanoSeedRootDto.getVolcanos()) {

           Optional<Volcanologist> optional =
                   volcanologistRepository.findByFirstNameAndLastName(volcano.getFirstName(), volcano.getLastName());

           Optional<Volcano> optionalVolcano = volcanoRepository.findById(volcano.getExploringVolcano());
            if (!validationUtil.isValid(volcano) || optional.isPresent() || optionalVolcano.isEmpty()){

                sb.append("Invalid volcanologist\n");
                continue;
            }
            Volcanologist volcanologist = modelMapper.map(volcano, Volcanologist.class);
            volcanologist.setExploringVolcano(optionalVolcano.get());
            volcanologistRepository.save(volcanologist);
            sb.append(String.format("Successfully imported volcanologist %s %s",
                    volcanologist.getFirstName(), volcanologist.getLastName())).append(System.lineSeparator());

        }




        return sb.toString();
    }
}