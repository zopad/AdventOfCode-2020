import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class Task4 {

    @Test
    public void testTask4() {
        // Part 1 seems to be 233 but I counted 232. ¯\_(ツ)_/¯
        assertEquals(111, countValidPassports());
    }

    private int countValidPassports() {
        try {
            List<Passport> passports = new ArrayList<>();
            Scanner scanner = new Scanner(new File((Paths.get("input4.txt").toUri())));
            StringBuilder buffer = new StringBuilder();
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if (nextLine.isEmpty()) {
                    passports.add(new Passport(buffer.toString()));
                    buffer = new StringBuilder();
                } else {
                    buffer.append("\n").append(nextLine);
                }
            }
            return (int) passports.stream().filter(Passport::isValid).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static class Passport {
        String fields;
        Map<String, String> map;

        public Passport(String fields) {
            this.fields = fields;
            map = splitToMap(fields);
        }

        private Map<String, String> splitToMap(String fields) {
            HashMap<String, String> map = new HashMap<>();
            String[] kvPairs = fields.split("[ \n]");
            for (String kvPair : kvPairs) {
                if (kvPair.isEmpty()) {
                    continue;
                }
                String[] split = kvPair.split(":");
                map.put(split[0], split[1]);
            }
            return map;
        }

        public boolean hasAllMandatoryFields() {
            return fields.contains("iyr:") && fields.contains("ecl:") && fields.contains("byr") &&
                    fields.contains("eyr:") && fields.contains("hgt:") && fields.contains("hcl:")
                    && fields.contains("pid:");
        }

        public boolean isValid() {
            return hasAllMandatoryFields() && validateFields(map);
        }

        private boolean validateFields(Map<String, String> map) {
            for (String key : map.keySet()) {
                switch (key) {
                    case "byr":
                        int byr = Integer.parseInt(map.get(key));
                        if (byr < 1920 || byr > 2002) {
                            return false;
                        }
                        break;
                    case "iyr":
                        int iyr = Integer.parseInt(map.get(key));
                        if (iyr < 2010 || iyr > 2020) {
                            return false;
                        }
                        break;
                    case "eyr":
                        int eyr = Integer.parseInt(map.get(key));
                        if (eyr < 2020 || eyr > 2030) {
                            return false;
                        }
                        break;
                    case "hgt":
                        String heightWithUnit = map.get(key);
                        if (heightWithUnit.contains("cm")) {
                            int height = Integer.parseInt(heightWithUnit.split("cm")[0]);
                            if (height < 150 || height > 193) {
                                return false;
                            }
                        } else if (heightWithUnit.contains("in")) {
                            int height = Integer.parseInt(heightWithUnit.split("in")[0]);
                            if (height < 59 || height > 76) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                        break;
                    case "hcl":
                        String colorHex = map.get(key);
                        boolean validHex = Pattern.compile("^#([0-9]|[a-f]){6}").matcher(colorHex).matches();
                        if (!validHex) {
                            return false;
                        }
                        break;
                    case "ecl":
                        String eyeColor = map.get(key);
                        boolean validEyeColor = Pattern.compile("amb|blu|brn|gry|grn|hzl|oth").matcher(eyeColor).matches();
                        if (!validEyeColor) {
                            return false;
                        }
                        break;
                    case "pid":
                        String passportId = map.get(key);
                        boolean validPassport = Pattern.compile("[0-9]{9}").matcher(passportId).matches();
                        if (!validPassport) {
                            return false;
                        }
                        break;
                    case "cid":
                        // ignored
                        break;
                    default:
                        System.err.println("Unrecognized key: " + key);
                }
            }
            return true;
        }
    }
}
