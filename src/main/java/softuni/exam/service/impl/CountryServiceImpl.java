package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private static final String COUNTRY_PATH = "src/main/resources/files/json/countries.json";

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountrySeedDto[] countrySeedDtos = gson.fromJson(readCountriesFromFile(), CountrySeedDto[].class);


        for (CountrySeedDto countrySeedDto : countrySeedDtos) {
            Optional<Country> optional = countryRepository.findByName(countrySeedDto.getName());

            if (!validationUtil.isValid(countrySeedDto) || optional.isPresent()){

                sb.append("Invalid country\n");
                continue;
            }

            Country country = modelMapper.map(countrySeedDto, Country.class);
            countryRepository.save(country);
            sb.append(String.format("Successfully imported country %s - %s"
                    , country.getName(), country.getCapital())).append(System.lineSeparator());

        }


        return sb.toString();
    }
}
