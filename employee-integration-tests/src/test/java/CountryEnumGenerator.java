import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountryEnumGenerator {
    public static void main(String[] args) {

        String[] countryCodes = Locale.getISOCountries();
        List<Country> list = new ArrayList<Country>(countryCodes.length);
        final List<String> strings = Arrays.asList(countryCodes);

        for (String cc : countryCodes) {
            list.add(new Country(cc.toUpperCase(), new Locale("", cc).getDisplayCountry()));
        }

        Collections.sort(list);

        for (Country c : list) {
            System.out.println("/**" + c.getName() + "*/");
            System.out.println(c.getCode() + "(\"" + c.getName() + "\"),");
        }

    }

    @Test
    public void parseCCodes() {
        Path path = Paths.get("C:\\Dev\\learning\\javaee-project\\employee-integration-tests\\src\\test\\resources\\country_continent.csv");
        try {
            final Map<String, List<String>> ccMap = Maps.transformValues(
                    Files.lines(path).collect(Collectors.groupingBy(line -> line.split(",")[1])),
                    list -> list.stream().map(line -> line.split(",")[0]).collect(Collectors.toList()));

            ccMap.forEach((key, value) -> System.out.println(Joiner.on(", ").join(ImmutableList.builder().add(key).addAll(value).build().stream().map(e -> "\"" + e + "\"").iterator())));


            Map<String, String> r = Stream.of("de,eu", "fr,eu", "us,na").collect(Collectors.toMap(line -> line.split(",")[0], line -> line.split(",")[1]));
            System.out.println("r = " + r);




            /*Files.lines(path).forEach(string -> {
                String[] codes = string.split(",");
                String continentCode = codes[1];
                String countryCode = codes[0];
                List<String> contry_codes = ccMap.get(continentCode);
                if(contry_codes == null)
                    ccMap.put(continentCode,new ArrayList<>());

                contry_codes.add(countryCode);
            });

            ccMap.forEach((key, countries)->{
                String res = "";
                System.out.println("-- Continrent Key: " + key);
                countries.forEach(country -> {res += "\""+country+"\","});
            });*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class Country implements Comparable<Country> {
    private String code;
    private String name;

    public Country(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Country o) {
        return this.name.compareTo(o.name);
    }
}
