package com.example.lichthidaubongda;

public enum URL {
    urlCheckGD      ("https://callmedunno.000webhostapp.com/checkExistGD.php"),
    urlGetDataGD    ("https://callmedunno.000webhostapp.com/getDataGD.php"),
    urlInsertGD     ("https://callmedunno.000webhostapp.com/insertDataGD.php"),
    urlCheckLTD     ("https://callmedunno.000webhostapp.com/checkExistLTD.php"),
    urlInsertLTD    ("https://callmedunno.000webhostapp.com/insertDataLTD.php"),
    urlGetDataLTD   ("https://callmedunno.000webhostapp.com/getDataLTD.php"),
    urlUpdateDataLTD("https://callmedunno.000webhostapp.com/updateDataLTD.php"),
    urlGetMaGiaiDau ("https://callmedunno.000webhostapp.com/getMaGiaiDau.php");

    private final String Link;

    URL(String s) {
        this.Link = s;
    }

    public String GetUrl(){
        return Link;
    }
}
