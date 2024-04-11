package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoSeedDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private final VolcanoRepository volcanoRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final CountryRepository countryRepository;

    private static final String VOLCANO_PATH = "src/main/resources/files/json/volcanoes.json";

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, CountryRepository countryRepository) {
        this.volcanoRepository = volcanoRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANO_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();


        VolcanoSeedDto[] volcanoSeedDtos = gson.fromJson(readVolcanoesFileContent(), VolcanoSeedDto[].class);

        for (VolcanoSeedDto volcanoSeedDto : volcanoSeedDtos) {
            Optional<Volcano> optional = volcanoRepository.findByName(volcanoSeedDto.getName());

            if (!validationUtil.isValid(volcanoSeedDto) || optional.isPresent()){
                sb.append("Invalid volcano\n");
                continue;
            }

            Volcano volcano = modelMapper.map(volcanoSeedDto, Volcano.class);
            volcano.setCountry(this.countryRepository.findById(volcanoSeedDto.getCountry()).get());
            volcanoRepository.save(volcano);
            sb.append(String.format("Successfully imported volcano %s of type %s"
                    , volcano.getName(), volcano.getVolcanoType())).append(System.lineSeparator());
        }



        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {

            return this.volcanoRepository.findAllByNameAndLocationOver3000()
                    .stream()
                    .map(s-> String.format("Volcano: %s\n" +
                            "   *Located in: %s\n" +
                            "   **Elevation: %d\n" +
                            "   ***Last eruption on: %s%n",s.getName(),s.getCountry().getName()
                    ,s.getElevation(),s.getLastEruption())).collect(Collectors.joining());
    }
}