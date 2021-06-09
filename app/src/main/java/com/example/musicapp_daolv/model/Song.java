package com.example.musicapp_daolv.model;

public class Song {
    private String sTenBai;
    private String sPath;
    private String sCaSi;
    private byte[] bAnh;

    public Song(String sTenBai, String sPath, String sCaSi, byte[] bAnh) {
        this.sTenBai = sTenBai;
        this.sPath = sPath;
        this.sCaSi = sCaSi;
        this.bAnh = bAnh;
    }

    public Song(String sTenBai, String sPath) {
        this.sTenBai = sTenBai;
        this.sPath = sPath;
    }

    public Song(String sTenBai, String sPath, String sCaSi) {
        this.sTenBai = sTenBai;
        this.sPath = sPath;
        this.sCaSi = sCaSi;
    }

    public String getsTenBai() {
        return sTenBai;
    }

    public void setsTenBai(String sTenBai) {
        this.sTenBai = sTenBai;
    }

    public String getsPath() {
        return sPath;
    }

    public void setsPath(String sPath) {
        this.sPath = sPath;
    }

    public String getsCaSi() {
        return sCaSi;
    }

    public void setsCaSi(String sCaSi) {
        this.sCaSi = sCaSi;
    }

    public byte[] getbAnh() {
        return bAnh;
    }

    public void setbAnh(byte[] bAnh) {
        this.bAnh = bAnh;
    }
}
