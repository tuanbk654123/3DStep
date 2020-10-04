package com.example.a3dstep.View.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a3dstep.Constant.SharePreference;
import com.example.a3dstep.Model.Data;
import com.example.a3dstep.Presenter.LoginPresenter;
import com.example.a3dstep.R;
import com.example.a3dstep.View.MainActivity;
import com.example.a3dstep.View.StartActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginView {

	private static final String TAG = "LoginActivity" ;
	Button btnLogin;
	LoginButton loginFacebookButton;
	LoginPresenter loginPresenter;
	EditText edtUser, edtPassword;
	CallbackManager callbackManager;
	SharedPreferences loginPreferences;
	private Boolean saveLogin;

	public SharedPreferences.Editor loginPrefsEditor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SharePreference.getInstance().Init(getApplication());
		//SharePreference.Init(getApplication());
		addControl();
		loadSetting();
		addEvent();
	}
	private void addControl() {
		btnLogin = (Button) findViewById(R.id.btnLogin);
		edtUser = (EditText) findViewById(R.id.edtUserLogin);
		edtPassword = (EditText) findViewById(R.id.edtPasswordLogin);
		loginFacebookButton = (LoginButton) findViewById(R.id.loginFacebookButton);

		callbackManager = CallbackManager.Factory.create();
		loginFacebookButton.setReadPermissions(Arrays.asList("email","public_profile"));
		checkLoginStatus();
	}

	private void addEvent() {
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String user = String.valueOf(edtUser.getText());
				String password = String.valueOf(edtPassword.getText());
				loginPresenter = new LoginPresenter(LoginActivity.this);
				loginPresenter.login(user,password);
			}
		});

		loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				SharePreference.getInstance().setSaveLogin(true);
				SharePreference.getInstance().setLoginMode(Data.LOGIN_WITH_FACEBOOK);
				Log.e(TAG, "onSuccess");
			}
			@Override
			public void onCancel() {
				Log.e(TAG, "onCancel");

			}
			@Override
			public void onError(FacebookException error) {
				Log.e(TAG, "onError: " + error);
			}
		});
	}
	private void loadSetting() {
		/*loginPreferences = getSharedPreferences("Setting", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();*/
		//saveLogin = loginPreferences.getBoolean("saveLogin", false);

		saveLogin = SharePreference.getInstance().getSaveLogin();

		if (saveLogin == true) {
			edtUser.setText(SharePreference.getInstance().getUserLogin());
			edtPassword.setText(SharePreference.getInstance().getPasswordLogin());

			Intent intent = new Intent(LoginActivity.this, StartActivity.class);
			startActivity(intent);
		}
		//Check if user is currently logged in
		if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){
			Intent intent = new Intent(LoginActivity.this, StartActivity.class);
			startActivity(intent);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

		callbackManager.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
		@Override
		protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
			if(currentAccessToken == null){
				Toast.makeText(LoginActivity.this,"User Log Out",Toast.LENGTH_SHORT);
			}
			else {
				loadUserProfile(currentAccessToken);
			}
		}
	};
	private void loadUserProfile ( AccessToken newAccessToken){
		GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
			@Override
			public void onCompleted(JSONObject object, GraphResponse response) {
				try {
					String name = object.getString("name");
					String id = object.getString("id");
					String image_url = "https://graph.facebook.com/"+id+"/picture?type=large";

					RequestOptions requestOptions = new RequestOptions();
					requestOptions.dontAnimate();
					//Glide.with(MainActivity.this).load(image_url).into(circleImageView);

					SharePreference.getInstance().setNameFaceBook(name);
					SharePreference.getInstance().setIdFacebook(id);
					SharePreference.getInstance().setImageUrlFacebook(image_url);

					Log.e(TAG,name );
					Intent intent = new Intent(LoginActivity.this, StartActivity.class);
					startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
					Log.e(TAG, "onError: " + e);
				}
			}
		});
		Bundle parameter = new Bundle();
		parameter.putString("fileds","name,id");
		request.setParameters(parameter);
		request.executeAsync();
	}


	public void checkLoginStatus(){
		if(AccessToken.getCurrentAccessToken() != null){
			loadUserProfile(AccessToken.getCurrentAccessToken() );
		}
	}
	@Override
	public void userNeedFill() {
		edtUser.setFocusable(true);
		edtUser.setError("Need type user name");
	}

	@Override
	public void passNeedFill() {
		edtPassword.setFocusable(true);
		edtPassword.setError("Need type password");
	}

	@Override
	public void loginSuccess() {
		SharePreference.getInstance().setLoginMode(Data.LOGIN_NORMAL);
		SharePreference.getInstance().setSaveLogin(true);
		SharePreference.getInstance().setUserLogin(edtUser.getText()+"");
		SharePreference.getInstance().setPasswordLogin(edtPassword.getText()+"");

		Intent intent = new Intent(LoginActivity.this, StartActivity.class);
		LoginActivity.this.startActivity(intent);
	}
}
