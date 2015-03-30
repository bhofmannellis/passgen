/**
 * @author - Blaise Hofmann-Ellis
 * 
 * Brief: Android app for generating passwords which are secure and easy to remember. Started as practice for my Android class and hope to continue development as time permits.
 * 
 * Target Platform : Android 4.4 KitKat
 * 
 * Possible Future Features : User-added length; user-added seed words/numbers (dangerous!); Slider for strength vs. memorability
 * 
 * Notes: WordList2.txt adapted from Alan Beale's public domain Core Vocabulary ESL word
 * lists. More information here: http://wordlist.sourceforge.net/12dicts-readme.html 
 * Accessed at http://wordlist.aspell.net/12dicts/
 * 
 * Status : In Progress
 */

package edu.arizona.cs.passgen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	InputStream wordInput;
	ArrayList<String> wordsList;
	Scanner scan;
	Random rng;

	EditText passText;
	Button genButton;

	String word1;
	String word2;
	String word3;

	String tempPass;

	int listLen;

	public void generatePassword(View v) {

		word1 = wordsList.get(rng.nextInt(listLen));
		word2 = wordsList.get(rng.nextInt(listLen));
		word3 = wordsList.get(rng.nextInt(listLen));

		tempPass = word1 + " " + word2 + " " + word3;

	}

	// TODO 01 : Implement scrambling of word-only passwords

	// TODO 02 : Continue cultivation of wordlist

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		passText = (EditText) findViewById(R.id.passText);
		passText.setText("Press button to generate a password!");

		genButton = (Button) findViewById(R.id.genButton);
		genButton.setOnClickListener(this);

		tempPass = word1 = word2 = word3 = "";

		wordsList = new ArrayList<String>();

		try {
			wordInput = getApplicationContext().getAssets().open(
					"Wordlist2.txt");
		} catch (IOException e) {

			Toast.makeText(getApplicationContext(),
					"Failed to open Wordlist file", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		scan = new Scanner(wordInput);

		while (scan.hasNextLine()) {
			wordsList.add(scan.nextLine());
		}

		rng = new Random();
		listLen = wordsList.size() - 1;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == genButton.getId()) {

			generatePassword(v);

			passText.setText(tempPass);

			Toast.makeText(getApplicationContext(), "Password Generated",
					Toast.LENGTH_SHORT).show();

		}
	}
}