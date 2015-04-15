/**
 * @author - Blaise Hofmann-Ellis
 * 
 * Brief: Android app for generating passwords which are secure and easy to remember. Started as practice for my Android class and hope to continue development as time permits.
 * 
 * Target Platform : Android 4.4 KitKat
 * 
 * Possible Future Features : User-added length; user-added seed words/numbers (dangerous!); Slider for strength vs. memorability
 * 
 * Notes: WordListAll.txt adapted from Alan Beale's public domain Core Vocabulary ESL word
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

	private InputStream wordInput;
	private ArrayList<String> wordsList;
	private Scanner scan;
	private Random rng;

	private EditText passText;
	private Button genButton;

	private String word1;
	private String word2;
	//private String word3;

	private String tempPass;

	private int listLen;

	// TODO : Implement loading splash screen
	
	// TODO : Tweak scrambling of word-only passwords

	// TODO : Continue cultivation of wordlist
	
	// TODO : Write password strength/length checker and run it before returning final password

	// generatePassword picks two 5-9 letter words from the wordlist and applies transformations to them.
	private void generatePassword(View v) {

		word1 = wordsList.get(rng.nextInt(listLen));
		while (word1.length() > 9 || word1.length() < 5){
			word1 = wordsList.get(rng.nextInt(listLen));
		}
		
		word2 = wordsList.get(rng.nextInt(listLen));
		while (word2.length() > 9 || word2.length() < 5){
			word2 = wordsList.get(rng.nextInt(listLen));
		}
		
		word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1);
		word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1);

		tempPass = randomSymStr() + scramblePass(word1) + "_" + scramblePass(word2) + rng.nextInt(99) + randomSymStr(); //+ " " + word3;
	}
	
	// Method to return a random symbol as a single-character string
	private String randomSymStr(){
		
		int randSymNum = rng.nextInt(18);
		
		switch (randSymNum){
		
		case 0:
			return "!";
		case 1:
			return "~";
		case 2:
			return "@";
		case 3:
			return "#";
		case 4:
			return "$";
		case 5:
			return "%";
		case 6:
			return "^";
		case 7:
			return "&";
		case 8:
			return "*";
		case 9:
			return "(";
		case 10:
			return ")";
		case 11:
			return "-";
		case 12:
			return "+";
		case 13:
			return "_";
		case 14:
			return "=";
		case 15:
			return "<";
		case 16:
			return ">";
		case 17:
			return ".";
		default:
			return "!";
		}
		
	}
	
	// Iterate through input String replacing letters with a similar-looking number
	private String scramblePass( String passIn ){
		
		passIn += " ";
		
		for (int i = 1; i < passIn.length() - 2; i++){
			
			passIn = replaceCharWithNum(passIn, i );				
		}
				
		return passIn.substring( 0, passIn.length() - 1);	
	}

	// Decomposed method for replacing letters with numbers.
	private String replaceCharWithNum( String stringIn, int index ){
		
		int percentChance = rng.nextInt(100);
		
		if ( percentChance < 0 )
			return stringIn;
		
		switch (stringIn.charAt(index)){
		
		case 'a':
			return stringIn.substring(0, index) + "4" + stringIn.substring(index + 1);
		case 'e':
			return stringIn.substring(0, index) + "3" + stringIn.substring(index + 1);
		case 'i':
			return stringIn.substring(0, index) + "1" + stringIn.substring(index + 1);
		case 'o':
			return stringIn.substring(0, index) + "0" + stringIn.substring(index + 1);
		case 't':
			return stringIn.substring(0, index) + "7" + stringIn.substring(index + 1);
		case 's':
			return stringIn.substring(0, index) + "5" + stringIn.substring(index + 1);
		default:
			return stringIn;
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		passText = (EditText) findViewById(R.id.passText);
		passText.setText("Press button to generate a password!");

		genButton = (Button) findViewById(R.id.genButton);
		genButton.setOnClickListener(this);

		tempPass = word1 = word2 = "";

		wordsList = new ArrayList<String>();

		try {
			wordInput = getApplicationContext().getAssets().open(
					"Wordlist59.txt");
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