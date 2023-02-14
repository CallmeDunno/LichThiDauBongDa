package com.example.lichthidaubongda;

public enum URL {
    //192.168.137.169  192.168.43.214
    urlCheckGD      ("http://192.168.43.214:81/lichdabong/checkExistGD.php"),
    urlGetDataGD    ("http://192.168.43.214:81/lichdabong/getDataGD.php"),
    urlInsertGD     ("http://192.168.43.214:81/lichdabong/insertDataGD.php"),
    urlCheckLTD     ("http://192.168.43.214:81/lichdabong/checkExistLTD.php"),
    urlInsertLTD    ("http://192.168.43.214:81/lichdabong/insertDataLTD.php"),
    urlGetDataLTD   ("http://192.168.43.214:81/lichdabong/getDataLTD.php"),
    urlUpdateDataLTD("http://192.168.43.214:81/lichdabong/updateDataLTD.php"),
    urlGetMaGiaiDau ("http://192.168.43.214:81/lichdabong/getMaGiaiDau.php");

    private final String Link;

    URL(String s) {
        this.Link = s;
    }

    public String GetUrl(){
        return Link;
    }
}
