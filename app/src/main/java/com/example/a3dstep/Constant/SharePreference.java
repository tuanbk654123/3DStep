package com.example.a3dstep.Constant;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a3dstep.Model.Data;

import static android.content.Context.MODE_PRIVATE;

public class SharePreference {
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor loginPrefsEditor;
    // static variable single_instance of type Singleton
    private static SharePreference sharedPreferences_instance = null;
    public static SharePreference getInstance()
    {
        if (sharedPreferences_instance == null)
            sharedPreferences_instance = new SharePreference();
        return sharedPreferences_instance;
    }

    public void DeleteSharePre(){
        loginPrefsEditor.clear().commit();
    }

    public void Init(Context context) {
        sharedPreferences = context.getSharedPreferences("Setting", MODE_PRIVATE);
        loginPrefsEditor = sharedPreferences.edit();
    }

    public void setUriBackground(String uriBackground){
        loginPrefsEditor.putString("uriBackground",uriBackground);
        loginPrefsEditor.commit();
    }

    public String getUriBackground(){
        return sharedPreferences.getString("uriBackground", "false");
    }


    public void setUriAvata(String uriAvata){
        loginPrefsEditor.putString("uriAvata",uriAvata);
        loginPrefsEditor.commit();
    }

    public String getUriAvata(){
        return sharedPreferences.getString("uriAvata", "false");
    }

    public void setUserLogin(String username){
        loginPrefsEditor.putString("username",username);
        loginPrefsEditor.commit();
    }

    public String getUserLogin(){
        return sharedPreferences.getString("username", "false");
    }

    public void setPasswordLogin(String password){
        loginPrefsEditor.putString("password",password);
        loginPrefsEditor.commit();
    }

    public String getPasswordLogin(){
        return sharedPreferences.getString("password", "false");
    }

    public Boolean getSaveLogin(){
        return sharedPreferences.getBoolean("saveLogin", false);
    }

    public void setSaveLogin(Boolean saveLogin){
        loginPrefsEditor.putBoolean("saveLogin",saveLogin);
        loginPrefsEditor.commit();
    }

    public String getNameFaceBook(){
        return sharedPreferences.getString("nameFaceBook", "false");
    }

    public void setNameFaceBook(String name){
        loginPrefsEditor.putString("nameFaceBook",name);
        loginPrefsEditor.commit();
    }

    public String getImageUrlFacebook(){
        return sharedPreferences.getString("imageUrlFacebook", "false");
    }

    public void setImageUrlFacebook(String image_url){
        loginPrefsEditor.putString("imageUrlFacebook",image_url);
        loginPrefsEditor.commit();
    }

    public String getIdFacebook(){
        return sharedPreferences.getString("idFacebook", "false");
    }

    public void setIdFacebook(String id){
        loginPrefsEditor.putString("idFacebook",id+"");
        loginPrefsEditor.commit();
    }

    public int getLoginMode() {
        return sharedPreferences.getInt("loginMode", Data.LOGIN_NORMAL);
    }

    public void setLoginMode(int loginMode) {
        loginPrefsEditor.putInt("loginMode",loginMode);
        loginPrefsEditor.commit();
    }
}
