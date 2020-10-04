package com.example.a3dstep.Presenter;

import com.example.a3dstep.View.Login.LoginView;

public class LoginPresenter {

	private LoginView loginView;
	public LoginPresenter(LoginView loginView) {
		this.loginView = loginView;
	}

	public void login(String user, String password){
		if(user.isEmpty()){
			loginView.userNeedFill();
		}else if (password.isEmpty()){
			loginView.passNeedFill();
		}else {

			if(user.equals("admin") && password.equals("admin")){
				loginView.loginSuccess();
			}
		}
	}
}
