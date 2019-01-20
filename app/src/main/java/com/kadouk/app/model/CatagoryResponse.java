package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jahan on 10/18/18.
 */
//2
// pusheye model shamele ye seri model shabihe datayi hast ke az server migirim,
// darvaghe az ghabl behesh goftim ke chia gharare begirim ba che tag hayi va che jensi
// ke tuye class e APPIInterface(darkhasthamun az server) va activity ha estefadashun kardim
// bad az inke ino kamel check kardi content ro baz kon

public class CatagoryResponse {

    // contents datayi hast ke vase category ha migirim,
    // tuye contents esme app va desc va image va cost ro migirim vase nemayesh tu category
    @SerializedName("contents")
    private List<Contents> contents;
    // bala esme app va... gereftim inja name category ro migirim
    @SerializedName("catName")
    private String catName;

    public CatagoryResponse(List<Contents> contents, String catName) {
        this.contents = contents;
        this.catName = catName;
    }

    public List<Contents> getContents() {
        return contents;
    }

    public void setContents(List<Contents> contents) {
        this.contents = contents;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}