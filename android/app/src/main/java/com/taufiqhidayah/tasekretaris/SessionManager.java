package com.taufiqhidayah.tasekretaris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import org.json.JSONObject;

/**
 * Created by imastudio on 2/9/16.
 */
public class SessionManager {
    private static final String KEY_TOKEN = "tokenLogin";
    private static final String KEY_STATUS = "status";
    private static final String KEY_LOGIN = "isLogin";
    private static final int MODE_PRIVATE =102 ;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE =0;    Context c;

    //0 agar cuma bsa dibaca hp itu sendiri
    String PREF_NAME="OjekOnlinePref";

    //konstruktor
    public SessionManager(Context c){
        this.c = c;
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public  void createtoogle(String status){
        editor.putString(KEY_STATUS,status);
        editor.putBoolean(KEY_STATUS,true);
        editor.commit();

    }


    @SuppressLint("WrongConstant")
    public boolean isStatus(){
        pref = c.getSharedPreferences("com.taufiqhidayah.adapasardriver", MODE_PRIVATE);
        return  pref.getBoolean(KEY_STATUS,false);
    }
    //membuat session login
    public void cerateLoginSession(String token){
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
        //commit digunakan untuk menyimpan perubahan
    }
    //mendapatkan token
    public String getToken(){
        return pref.getString(KEY_TOKEN, "");
    }
    //cek login
    public boolean isLogin(){
        return pref.getBoolean(KEY_LOGIN, false);
    }
    //logout user
    public void logout(){
        editor.clear();
        editor.commit();
    }

    public void setTextStatus(String textStatus){
        editor.putString("txtstatus", textStatus);
        editor.commit();
    }
    public String getTextStatus(){

        return pref.getString("txtstatus", "");
    }
    public void setImg(Uri textStatus){
        editor.putString("txtstatus", String.valueOf(textStatus));
        editor.commit();
    }
    public String getImg(){

        return pref.getString("txtstatus", "");
    }
    public void setEmail(String email){
        editor.putString("email", email);
        editor.commit();
    }
    public void setStatus(Boolean status){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("btnstate", status);
        editor.commit();
    }
    public boolean getStatus(){

        return pref.getBoolean("btnstate", false);
    }
    public String getEmail(){
        return pref.getString("email", "");
    }
    public void setPhone(String phone_user){
        editor.putString("phone_user", phone_user);
        editor.commit();
    }
    public void setNama(String nama_user){
        editor.putString("nama_user", nama_user);
        editor.commit();
    }
    public String getNama(){

        return pref.getString("nama_user", "");
    }

    public String getPhone(){
        return pref.getString("phone_user", "");
    }
    public void setIduser(String id_user){
        editor.putString("id_user", id_user);
        editor.commit();
    }
    public String getIdUser(){
        return pref.getString("id_user", "");
    }


    public void setTokenfcm(String id_tokenfcm){
        editor.putString("fcm", id_tokenfcm);
        editor.commit();
    }
    public String getTokenfcm(){
        return pref.getString("fcm", "");
    }
    public void setSaldo(String saldo){
        editor.putString("deposit_jumlah", saldo);
        editor.commit();
    }
    public String getSaldo(){

        return pref.getString("deposit_jumlah", "");
    }

}
