package com.derteuffel.archives.helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryDetails {



    public CountryDetails() {
    }

    private ArrayList<String> divisions = new ArrayList<>(Arrays.asList("Informatique","Resources Humaine","Finances","Exploitation","Production","Maintenance","Logistique","Administration"));
    private ArrayList<String> regions = new ArrayList<>(Arrays.asList(
            "Adamaoua","Centre","Est","Extreme nord","Littoral","Nord","Nord-Ouest","Ouest","Sud","Sud-Ouest"
    ));
    private ArrayList<String> villes = new ArrayList<>(Arrays.asList(
            "Abandikiti",
            "Abong Mbang",
            "Akono",
            "Akom II",
            "Akonolinga",
            "Akwabana Island",
            "Ambam",
            "Ambas Island",
            "Bafia",
            "Bota",
            "Bafang",
            "Bélabo",
            "Babanki",
            "Bélel",
            "Belo",
            "Bertoua",
            "Bétaré Oya",
            "Buéa",
            "Bobia Island",
            "Bogo",
            "Bonabéri",
            "Bafoussam",
            "Bali",
            "Bamkim",
            "Bamusso",
            "Bamenda",
            "Bamendjou",
            "Bana",
            "Bekondo",
            "Bansoa",
            "Bazou",
            "Bandjoun",
            "Bangangté",
            "Batibo",
            "Banyo",
            "Baténa",
            "Batouri",
            "Campton Island",
            "Douala",
            "Dimako",
            "Dizangué",
            "Dibombari",
            "Diang",
            "Djohong",
            "Doumé",
            "Dschang",
            "Ébolowa",
            "Édéa",
            "Eséka",
            "Évodoula",
            "Ewuru Island",
            "Essé",
            "Fiari Island",
            "Foumbot",
            "Fumban",
            "Fundong",
            "Fontem",
            "Garoua",
            "Garoua Boulaï",
            "Guidder",
            "Hyoma Nawétia",
            "Idenao",
            "Ile Anna",
            "Île Bouhangué",
            "Île Dibongo",
            "Ile Djébalé",
            "Île Kwélé-Kwélé",
            "Île Malimba",
            "Ile Manoka",
            "Ile Miandjou",
            "Ile Ndonga",
            "Île Nicol",
            "Île Pongo",
            "Ile Wouri",
            "Ile Yatou",
            "Inikoi Island",
            "Jakiri",
            "Kabanha",
            "Kaélé",
            "Katéla",
            "Koza",
            "Kribi",
            "Kumba",
            "Kumbo",
            "Kontcha",
            "Kousséri",
            "Lagdo",
            "Limbé",
            "Loum",
            "Lucke Island",
            "Lolodorf",
            "Mindif",
            "Minjame",
            "Mokolo",
            "Mondoleh Island",
            "Mouanko",
            "Mouyouka",
            "Mundenba",
            "Mora",
            "Mutengene",
            "Mvangué",
            "Makary",
            "Mamfe",
            "Manjo",
            "Maroua",
            "Masonjo Island",
            "Mbalmayo",
            "Mbandjok",
            "Mbang",
            "Mbanga",
            "Mbankomo",
            "Mbégé",
            "Mbengwi",
            "Mbouda",
            "Me",
            "Meiganda",
            "Melong",
            "Minta",
            "Nanga Eboko",
            "Ndeba",
            "Ndélélé",
            "Ndikiniméki",
            "Ndom",
            "Nganbé",
            "Ngaundere",
            "Ngomedzap",
            "Ngorro",
            "Ngou",
            "Nguti",
            "Njinikom",
            "Nkoloboko",
            "Nkongsamba",
            "Nkoteng",
            "Ntui",
            "Obala",
            "Okoa",
            "Okola",
            "Ombessa",
            "Otérou",
            "Penja",
            "Piparla",
            "Pitoa",
            "Poli",
            "Pon",
            "Rey Bouba",
            "Saa",
            "Sangmélima",
            "Schiess Island",
            "Soden Island",
            "Tcholliré",
            "Tibati",
            "Tignère",
            "Tiko",
            "Tiko Island",
            "Tonga",
            "Wum",
            "Yabassi",
            "Yagoua",
            "Yaoundé",
            "Yokadouma",
            "Yoko"

    ));


    public ArrayList<String> getVilles() {
        return villes;
    }

    public void setVilles(ArrayList<String> villes) {
        this.villes = villes;
    }

    public ArrayList<String> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<String> regions) {
        this.regions = regions;
    }

    public ArrayList<String> getDivisions() {
        return divisions;
    }

    public void setDivisions(ArrayList<String> divisions) {
        this.divisions = divisions;
    }
}
