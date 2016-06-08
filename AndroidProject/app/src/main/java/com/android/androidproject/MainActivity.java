package com.android.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;



public class MainActivity extends Activity {

	RadioButton rbSkillTesting, rbLearnTech;
	Button bStartTest;
	boolean flag = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ---Hiding titlebar---
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);

		initialize();

	}

	public void btnStartClick(View v) {
		if (v.getId() == R.id.bStartNow) {
			if (rbSkillTesting.isChecked()) {
				Intent startQuestions = new Intent(MainActivity.this,
						com.android.androidproject.Levels.class);
				startActivity(startQuestions);
			} else if (rbLearnTech.isChecked()) {
				Intent learnSkills = new Intent(MainActivity.this,
						com.android.androidproject.AdditionSkill.class);
				startActivity(learnSkills);
				finish();
			}
		}
	}

	private void initialize() {
		// TODO Auto-generated method stub
		rbSkillTesting = (RadioButton) findViewById(R.id.rbSkillTesting);
		rbLearnTech = (RadioButton) findViewById(R.id.rbLearnTech);
		bStartTest = (Button) findViewById(R.id.bStartNow);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		
		case R.id.mAboutUs:
			Intent startAboutUs = new Intent(MainActivity.this, com.android.androidproject.AboutUs.class);
			startActivity(startAboutUs);
			break;
		
		case R.id.mExit:
			finish();
			break;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
