package com.keeboot.utils.dataHelpers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple converter to get state names or codes. Also contains Canadian and other values
 *
 * @author Govind Soni
 * @version 06/08/2018
 */
public class CountryMapper {
    // Mapping containers
    private static Map<String, String> countryNameToCode = new HashMap<String, String>();
    private static Map<String, String> countryCodeToName = new HashMap<String, String>();

    /**
     * Get the country code/abbreviation by the country name
     *
     * @author Govind Soni
     * @version 06/07/2018
     * @param name
     *            - Country name to look up and get code for
     * @return Country code based on name sent in
     */
    public static String getCountryCode(String name) {
        populateCountryCodeMap();
        return countryNameToCode.get(name);
    }

    /**
     * Get the countrt name by the country code/abbreviation
     *
     * @author Justin Phlegar
     * @version 12/07/2015 Justin Phlegar - original
     * @param code
     *            - country code/abbreviation to look up and get name for
     * @return country name based on code sent in
     */
    public static String getCountryName(String code) {
        populateCountryNameMap();
        return countryCodeToName.get(code.toUpperCase());
    }

    /**
     * @summary Populate map for name to code conversion
     * @author Govind Soni
     * @version 06/08/2018
     */
    private static void populateCountryCodeMap() {
        countryNameToCode.put("Afghanistan", "AF");
        countryNameToCode.put("Albania", "AL");
        countryNameToCode.put("Algeria", "DZ");
        countryNameToCode.put("Andorra", "AD");
        countryNameToCode.put("Angola", "AO");
        countryNameToCode.put("Antigua and Barbuda", "AG");
        countryNameToCode.put("Argentina", "AR");
        countryNameToCode.put("Armenia", "AM");
        countryNameToCode.put("Australia", "AU");
        countryNameToCode.put("Austria", "AT");
        countryNameToCode.put("Azerbaijan", "AZ");
        countryNameToCode.put("Bahamas", "BS");
        countryNameToCode.put("Bahrain", "BH");
        countryNameToCode.put("Bangladesh", "BD");
        countryNameToCode.put("Barbados", "BB");
        countryNameToCode.put("Belarus", "BY");
        countryNameToCode.put("Belgium", "BE");
        countryNameToCode.put("Belize", "BZ");
        countryNameToCode.put("Benin", "BJ");
        countryNameToCode.put("Bhutan", "BT");
        countryNameToCode.put("Bolivia (Plurinational State of)", "BO");
        countryNameToCode.put("Bosnia and Herzegovina", "BA");
        countryNameToCode.put("Botswana", "BW");
        countryNameToCode.put("Brazil", "BR");
        countryNameToCode.put("Brunei Darussalam", "BN");
        countryNameToCode.put("Bulgaria", "BG");
        countryNameToCode.put("Burkina Faso", "BF");
        countryNameToCode.put("Burundi", "BI");
        countryNameToCode.put("Cabo Verde", "CV");
        countryNameToCode.put("Cambodia", "KH");
        countryNameToCode.put("Cameroon", "CM");
        countryNameToCode.put("Canada", "CA");
        countryNameToCode.put("Central African Republic", "CF");
        countryNameToCode.put("Chad", "TD");
        countryNameToCode.put("Chile", "CL");
        countryNameToCode.put("China", "CN");
        countryNameToCode.put("Colombia", "CO");
        countryNameToCode.put("Comoros", "KM");
        countryNameToCode.put("Congo", "CG");
        countryNameToCode.put("Cook Islands", "CK");
        countryNameToCode.put("Costa Rica", "CR");
        countryNameToCode.put("Croatia", "HR");
        countryNameToCode.put("Cuba", "CU");
        countryNameToCode.put("Cyprus", "CY");
        countryNameToCode.put("Czechia", "CZ");
        countryNameToCode.put("Côte d'Ivoire", "CI");
        countryNameToCode.put("Democratic People's Republic of Korea", "KP");
        countryNameToCode.put("Democratic Republic of the Congo", "CD");
        countryNameToCode.put("Denmark", "DK");
        countryNameToCode.put("Djibouti", "DJ");
        countryNameToCode.put("Dominica", "DM");
        countryNameToCode.put("Dominican Republic", "DO");
        countryNameToCode.put("Ecuador", "EC");
        countryNameToCode.put("Egypt", "EG");
        countryNameToCode.put("El Salvador", "SV");
        countryNameToCode.put("Equatorial Guinea", "GQ");
        countryNameToCode.put("Eritrea", "ER");
        countryNameToCode.put("Estonia", "EE");
        countryNameToCode.put("Ethiopia", "ET");
        countryNameToCode.put("Faroe Islands (Associate Member)", "FO");
        countryNameToCode.put("Fiji", "FJ");
        countryNameToCode.put("Finland", "FI");
        countryNameToCode.put("France", "FR");
        countryNameToCode.put("Gabon", "GA");
        countryNameToCode.put("Gambia", "GM");
        countryNameToCode.put("Georgia", "GE");
        countryNameToCode.put("Germany", "DE");
        countryNameToCode.put("Ghana", "GH");
        countryNameToCode.put("Greece", "GR");
        countryNameToCode.put("Grenada", "GD");
        countryNameToCode.put("Guatemala", "GT");
        countryNameToCode.put("Guinea", "GN");
        countryNameToCode.put("Guinea-Bissau", "GW");
        countryNameToCode.put("Guyana", "GY");
        countryNameToCode.put("Haiti", "HT");
        countryNameToCode.put("Honduras", "HN");
        countryNameToCode.put("Hungary", "HU");
        countryNameToCode.put("Iceland", "IS");
        countryNameToCode.put("India", "IN");
        countryNameToCode.put("Indonesia", "ID");
        countryNameToCode.put("Iran (Islamic Republic of)", "IR");
        countryNameToCode.put("Iraq", "IQ");
        countryNameToCode.put("Ireland", "IE");
        countryNameToCode.put("Israel", "IL");
        countryNameToCode.put("Italy", "IT");
        countryNameToCode.put("Jamaica", "JM");
        countryNameToCode.put("Japan", "JP");
        countryNameToCode.put("Jordan", "JO");
        countryNameToCode.put("Kazakhstan", "KZ");
        countryNameToCode.put("Kenya", "KE");
        countryNameToCode.put("Kiribati", "KI");
        countryNameToCode.put("Kuwait", "KW");
        countryNameToCode.put("Kyrgyzstan", "KG");
        countryNameToCode.put("Lao People's Democratic Republic", "LA");
        countryNameToCode.put("Latvia", "LV");
        countryNameToCode.put("Lebanon", "LB");
        countryNameToCode.put("Lesotho", "LS");
        countryNameToCode.put("Liberia", "LR");
        countryNameToCode.put("Libya", "LY");
        countryNameToCode.put("Lithuania", "LT");
        countryNameToCode.put("Luxembourg", "LU");
        countryNameToCode.put("Madagascar", "MG");
        countryNameToCode.put("Malawi", "MW");
        countryNameToCode.put("Malaysia", "MY");
        countryNameToCode.put("Maldives", "MV");
        countryNameToCode.put("Mali", "ML");
        countryNameToCode.put("Malta", "MT");
        countryNameToCode.put("Marshall Islands", "MH");
        countryNameToCode.put("Mauritania", "MR");
        countryNameToCode.put("Mauritius", "MU");
        countryNameToCode.put("Mexico", "MX");
        countryNameToCode.put("Micronesia (Federated States of)", "FM");
        countryNameToCode.put("Monaco", "MC");
        countryNameToCode.put("Mongolia", "MN");
        countryNameToCode.put("Montenegro", "ME");
        countryNameToCode.put("Morocco", "MA");
        countryNameToCode.put("Mozambique", "MZ");
        countryNameToCode.put("Myanmar", "MM");
        countryNameToCode.put("Namibia", "NA");
        countryNameToCode.put("Nauru", "NR");
        countryNameToCode.put("Nepal", "NP");
        countryNameToCode.put("Netherlands", "NL");
        countryNameToCode.put("New Zealand", "NZ");
        countryNameToCode.put("Nicaragua", "NI");
        countryNameToCode.put("Niger", "NE");
        countryNameToCode.put("Nigeria", "NG");
        countryNameToCode.put("Niue", "NU");
        countryNameToCode.put("Norway", "NO");
        countryNameToCode.put("Oman", "OM");
        countryNameToCode.put("Pakistan", "PK");
        countryNameToCode.put("Palau", "PW");
        countryNameToCode.put("Panama", "PA");
        countryNameToCode.put("Papua New Guinea", "PG");
        countryNameToCode.put("Paraguay", "PY");
        countryNameToCode.put("Peru", "PE");
        countryNameToCode.put("Philippines", "PH");
        countryNameToCode.put("Poland", "PL");
        countryNameToCode.put("Portugal", "PT");
        countryNameToCode.put("Qatar", "QA");
        countryNameToCode.put("Republic of Korea", "KR");
        countryNameToCode.put("Republic of Moldova", "MD");
        countryNameToCode.put("Romania", "RO");
        countryNameToCode.put("Russian Federation", "RU");
        countryNameToCode.put("Rwanda", "RW");
        countryNameToCode.put("Saint Kitts and Nevis", "KN");
        countryNameToCode.put("Saint Lucia", "LC");
        countryNameToCode.put("Saint Vincent and the Grenadines", "VC");
        countryNameToCode.put("Samoa", "WS");
        countryNameToCode.put("San Marino", "SM");
        countryNameToCode.put("Sao Tome and Principe", "ST");
        countryNameToCode.put("Saudi Arabia", "SA");
        countryNameToCode.put("Senegal", "SN");
        countryNameToCode.put("Serbia", "RS");
        countryNameToCode.put("Seychelles", "SC");
        countryNameToCode.put("Sierra Leone", "SL");
        countryNameToCode.put("Singapore", "SG");
        countryNameToCode.put("Slovakia", "SK");
        countryNameToCode.put("Slovenia", "SI");
        countryNameToCode.put("Solomon Islands", "SB");
        countryNameToCode.put("Somalia", "SO");
        countryNameToCode.put("South Africa", "ZA");
        countryNameToCode.put("South Sudan", "SS");
        countryNameToCode.put("Spain", "ES");
        countryNameToCode.put("Sri Lanka", "LK");
        countryNameToCode.put("Sudan", "SD");
        countryNameToCode.put("Suriname", "SR");
        countryNameToCode.put("Swaziland", "SZ");
        countryNameToCode.put("Sweden", "SE");
        countryNameToCode.put("Switzerland", "CH");
        countryNameToCode.put("Syrian Arab Republic", "SY");
        countryNameToCode.put("Tajikistan", "TJ");
        countryNameToCode.put("Thailand", "TH");
        countryNameToCode.put("The former Yugoslav Republic of Macedonia", "MK");
        countryNameToCode.put("Timor-Leste", "TL");
        countryNameToCode.put("Togo", "TG");
        countryNameToCode.put("Tokelau (Associate Member)", "TK");
        countryNameToCode.put("Tonga", "TO");
        countryNameToCode.put("Trinidad and Tobago", "TT");
        countryNameToCode.put("Tunisia", "TN");
        countryNameToCode.put("Turkey", "TR");
        countryNameToCode.put("Turkmenistan", "TM");
        countryNameToCode.put("Tuvalu", "TV");
        countryNameToCode.put("Uganda", "UG");
        countryNameToCode.put("Ukraine", "UA");
        countryNameToCode.put("United Arab Emirates", "AE");
        countryNameToCode.put("United Kingdom", "GB");
        countryNameToCode.put("United Republic of Tanzania", "TZ");
        countryNameToCode.put("United States", "US");
        countryNameToCode.put("Uruguay", "UY");
        countryNameToCode.put("Uzbekistan", "UZ");
        countryNameToCode.put("Vanuatu", "VU");
        countryNameToCode.put("Venezuela (Bolivarian Republic of)", "VE");
        countryNameToCode.put("Viet Nam", "VN");
        countryNameToCode.put("Yemen", "YE");
        countryNameToCode.put("Zambia", "ZM");
        countryNameToCode.put("Zimbabwe", "ZW");
    }

    /**
     * @summary Populate map for code to name conversion
     * @author Govind Soni
     * @version 06/08/2015 Justin Phlegar - original
     */
    private static void populateCountryNameMap() {
        countryCodeToName.put("AF", "Afghanistan");
        countryCodeToName.put("AL", "Albania");
        countryCodeToName.put("DZ", "Algeria");
        countryCodeToName.put("AD", "Andorra");
        countryCodeToName.put("AO", "Angola");
        countryCodeToName.put("AG", "Antigua and Barbuda");
        countryCodeToName.put("AR", "Argentina");
        countryCodeToName.put("AM", "Armenia");
        countryCodeToName.put("AU", "Australia");
        countryCodeToName.put("AT", "Austria");
        countryCodeToName.put("AZ", "Azerbaijan");
        countryCodeToName.put("BS", "Bahamas");
        countryCodeToName.put("BH", "Bahrain");
        countryCodeToName.put("BD", "Bangladesh");
        countryCodeToName.put("BB", "Barbados");
        countryCodeToName.put("BY", "Belarus");
        countryCodeToName.put("BE", "Belgium");
        countryCodeToName.put("BZ", "Belize");
        countryCodeToName.put("BJ", "Benin");
        countryCodeToName.put("BT", "Bhutan");
        countryCodeToName.put("BO", "Bolivia (Plurinational State of)");
        countryCodeToName.put("BA", "Bosnia and Herzegovina");
        countryCodeToName.put("BW", "Botswana");
        countryCodeToName.put("BR", "Brazil");
        countryCodeToName.put("BN", "Brunei Darussalam");
        countryCodeToName.put("BG", "Bulgaria");
        countryCodeToName.put("BF", "Burkina Faso");
        countryCodeToName.put("BI", "Burundi");
        countryCodeToName.put("CV", "Cabo Verde");
        countryCodeToName.put("KH", "Cambodia");
        countryCodeToName.put("CM", "Cameroon");
        countryCodeToName.put("CA", "Canada");
        countryCodeToName.put("CF", "Central African Republic");
        countryCodeToName.put("TD", "Chad");
        countryCodeToName.put("CL", "Chile");
        countryCodeToName.put("CN", "China");
        countryCodeToName.put("CO", "Colombia");
        countryCodeToName.put("KM", "Comoros");
        countryCodeToName.put("CG", "Congo");
        countryCodeToName.put("CK", "Cook Islands");
        countryCodeToName.put("CR", "Costa Rica");
        countryCodeToName.put("HR", "Croatia");
        countryCodeToName.put("CU", "Cuba");
        countryCodeToName.put("CY", "Cyprus");
        countryCodeToName.put("CZ", "Czechia");
        countryCodeToName.put("CI", "Côte d'Ivoire");
        countryCodeToName.put("KP", "Democratic People's Republic of Korea");
        countryCodeToName.put("CD", "Democratic Republic of the Congo");
        countryCodeToName.put("DK", "Denmark");
        countryCodeToName.put("DJ", "Djibouti");
        countryCodeToName.put("DM", "Dominica");
        countryCodeToName.put("DO", "Dominican Republic");
        countryCodeToName.put("EC", "Ecuador");
        countryCodeToName.put("EG", "Egypt");
        countryCodeToName.put("SV", "El Salvador");
        countryCodeToName.put("GQ", "Equatorial Guinea");
        countryCodeToName.put("ER", "Eritrea");
        countryCodeToName.put("EE", "Estonia");
        countryCodeToName.put("ET", "Ethiopia");
        countryCodeToName.put("FO", "Faroe Islands (Associate Member)");
        countryCodeToName.put("FJ", "Fiji");
        countryCodeToName.put("FI", "Finland");
        countryCodeToName.put("FR", "France");
        countryCodeToName.put("GA", "Gabon");
        countryCodeToName.put("GM", "Gambia");
        countryCodeToName.put("GE", "Georgia");
        countryCodeToName.put("DE", "Germany");
        countryCodeToName.put("GH", "Ghana");
        countryCodeToName.put("GR", "Greece");
        countryCodeToName.put("GD", "Grenada");
        countryCodeToName.put("GT", "Guatemala");
        countryCodeToName.put("GN", "Guinea");
        countryCodeToName.put("GW", "Guinea-Bissau");
        countryCodeToName.put("GY", "Guyana");
        countryCodeToName.put("HT", "Haiti");
        countryCodeToName.put("HN", "Honduras");
        countryCodeToName.put("HU", "Hungary");
        countryCodeToName.put("IS", "Iceland");
        countryCodeToName.put("IN", "India");
        countryCodeToName.put("ID", "Indonesia");
        countryCodeToName.put("IR", "Iran (Islamic Republic of)");
        countryCodeToName.put("IQ", "Iraq");
        countryCodeToName.put("IE", "Ireland");
        countryCodeToName.put("IL", "Israel");
        countryCodeToName.put("IT", "Italy");
        countryCodeToName.put("JM", "Jamaica");
        countryCodeToName.put("JP", "Japan");
        countryCodeToName.put("JO", "Jordan");
        countryCodeToName.put("KZ", "Kazakhstan");
        countryCodeToName.put("KE", "Kenya");
        countryCodeToName.put("KI", "Kiribati");
        countryCodeToName.put("KW", "Kuwait");
        countryCodeToName.put("KG", "Kyrgyzstan");
        countryCodeToName.put("LA", "Lao People's Democratic Republic");
        countryCodeToName.put("LV", "Latvia");
        countryCodeToName.put("LB", "Lebanon");
        countryCodeToName.put("LS", "Lesotho");
        countryCodeToName.put("LR", "Liberia");
        countryCodeToName.put("LY", "Libya");
        countryCodeToName.put("LT", "Lithuania");
        countryCodeToName.put("LU", "Luxembourg");
        countryCodeToName.put("MG", "Madagascar");
        countryCodeToName.put("MW", "Malawi");
        countryCodeToName.put("MY", "Malaysia");
        countryCodeToName.put("MV", "Maldives");
        countryCodeToName.put("ML", "Mali");
        countryCodeToName.put("MT", "Malta");
        countryCodeToName.put("MH", "Marshall Islands");
        countryCodeToName.put("MR", "Mauritania");
        countryCodeToName.put("MU", "Mauritius");
        countryCodeToName.put("MX", "Mexico");
        countryCodeToName.put("FM", "Micronesia (Federated States of)");
        countryCodeToName.put("MC", "Monaco");
        countryCodeToName.put("MN", "Mongolia");
        countryCodeToName.put("ME", "Montenegro");
        countryCodeToName.put("MA", "Morocco");
        countryCodeToName.put("MZ", "Mozambique");
        countryCodeToName.put("MM", "Myanmar");
        countryCodeToName.put("NA", "Namibia");
        countryCodeToName.put("NR", "Nauru");
        countryCodeToName.put("NP", "Nepal");
        countryCodeToName.put("NL", "Netherlands");
        countryCodeToName.put("NZ", "New Zealand");
        countryCodeToName.put("NI", "Nicaragua");
        countryCodeToName.put("NE", "Niger");
        countryCodeToName.put("NG", "Nigeria");
        countryCodeToName.put("NU", "Niue");
        countryCodeToName.put("NO", "Norway");
        countryCodeToName.put("OM", "Oman");
        countryCodeToName.put("PK", "Pakistan");
        countryCodeToName.put("PW", "Palau");
        countryCodeToName.put("PA", "Panama");
        countryCodeToName.put("PG", "Papua New Guinea");
        countryCodeToName.put("PY", "Paraguay");
        countryCodeToName.put("PE", "Peru");
        countryCodeToName.put("PH", "Philippines");
        countryCodeToName.put("PL", "Poland");
        countryCodeToName.put("PT", "Portugal");
        countryCodeToName.put("QA", "Qatar");
        countryCodeToName.put("KR", "Republic of Korea");
        countryCodeToName.put("MD", "Republic of Moldova");
        countryCodeToName.put("RO", "Romania");
        countryCodeToName.put("RU", "Russian Federation");
        countryCodeToName.put("RW", "Rwanda");
        countryCodeToName.put("KN", "Saint Kitts and Nevis");
        countryCodeToName.put("LC", "Saint Lucia");
        countryCodeToName.put("VC", "Saint Vincent and the Grenadines");
        countryCodeToName.put("WS", "Samoa");
        countryCodeToName.put("SM", "San Marino");
        countryCodeToName.put("ST", "Sao Tome and Principe");
        countryCodeToName.put("SA", "Saudi Arabia");
        countryCodeToName.put("SN", "Senegal");
        countryCodeToName.put("RS", "Serbia");
        countryCodeToName.put("SC", "Seychelles");
        countryCodeToName.put("SL", "Sierra Leone");
        countryCodeToName.put("SG", "Singapore");
        countryCodeToName.put("SK", "Slovakia");
        countryCodeToName.put("SI", "Slovenia");
        countryCodeToName.put("SB", "Solomon Islands");
        countryCodeToName.put("SO", "Somalia");
        countryCodeToName.put("ZA", "South Africa");
        countryCodeToName.put("SS", "South Sudan");
        countryCodeToName.put("ES", "Spain");
        countryCodeToName.put("LK", "Sri Lanka");
        countryCodeToName.put("SD", "Sudan");
        countryCodeToName.put("SR", "Suriname");
        countryCodeToName.put("SZ", "Swaziland");
        countryCodeToName.put("SE", "Sweden");
        countryCodeToName.put("CH", "Switzerland");
        countryCodeToName.put("SY", "Syrian Arab Republic");
        countryCodeToName.put("TJ", "Tajikistan");
        countryCodeToName.put("TH", "Thailand");
        countryCodeToName.put("MK", "The former Yugoslav Republic of Macedonia");
        countryCodeToName.put("TL", "Timor-Leste");
        countryCodeToName.put("TG", "Togo");
        countryCodeToName.put("TK", "Tokelau (Associate Member)");
        countryCodeToName.put("TO", "Tonga");
        countryCodeToName.put("TT", "Trinidad and Tobago");
        countryCodeToName.put("TN", "Tunisia");
        countryCodeToName.put("TR", "Turkey");
        countryCodeToName.put("TM", "Turkmenistan");
        countryCodeToName.put("TV", "Tuvalu");
        countryCodeToName.put("UG", "Uganda");
        countryCodeToName.put("UA", "Ukraine");
        countryCodeToName.put("AE", "United Arab Emirates");
        countryCodeToName.put("GB", "United Kingdom");
        countryCodeToName.put("TZ", "United Republic of Tanzania");
        countryCodeToName.put("US", "United States of America");
        countryCodeToName.put("UY", "Uruguay");
        countryCodeToName.put("UZ", "Uzbekistan");
        countryCodeToName.put("VU", "Vanuatu");
        countryCodeToName.put("VE", "Venezuela (Bolivarian Republic of)");
        countryCodeToName.put("VN", "Viet Nam");
        countryCodeToName.put("YE", "Yemen");
        countryCodeToName.put("ZM", "Zambia");
        countryCodeToName.put("ZW", "Zimbabwe");
    }
}