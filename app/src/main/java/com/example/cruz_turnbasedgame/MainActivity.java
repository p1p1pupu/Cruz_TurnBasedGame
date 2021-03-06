package com.example.cruz_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //creating objects
    TextView txtHeroHP , txtHeroDamage , txtEnemyHP , txtEnemyDamage , txtTurnCount , txtGameLog;
    Button btnNextTurn , btnHeal , btnIncreaseDMG , btnNewGame ;
    //imageview
    ImageView imgHero , imgEnemy;

    //Hero Stats
    int heroHP = 1000 ;
    int heroMinDamage = 150;
    int heroMaxDamage = 350;
    int heroMinHeal = 300 ;
    int heroMaxHeal= 850 ;
    int heroDamage ;
    Boolean heroDamageIncrease = false;

    //Enemy Stats
    int enemyHP = 3000 ;
    int enemyMinDamage = 50;
    int enemyMaxDamage = 250 ;
    int enemyDamage ;

    // Turn Count
    int turnCount = 0 ;

    //randomizer
    Random randomizer = new Random() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hides title banner upon startup
        requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //initially this was just under the super.OnCreate method
        setContentView(R.layout.activity_main);

        //set the xml stuff to the stuff here
        txtHeroHP = findViewById(R.id.txtHeroHP) ;
        txtHeroDamage = findViewById(R.id.txtHeroDamage) ;
        txtEnemyHP = findViewById(R.id.txtEnemyHP) ;
        txtEnemyDamage = findViewById(R.id.txtEnemyDamage) ;
        txtTurnCount = findViewById(R.id.txtTurnCount) ;
        txtGameLog = findViewById(R.id.txtGameLog) ;
        btnNextTurn = findViewById(R.id.btnNextTurn) ;
        btnHeal = findViewById(R.id.btnHeal) ;
        btnIncreaseDMG = findViewById(R.id.btnIncreaseDMG) ;
        btnNewGame = findViewById(R.id.btnNewGame) ;

        //images
        imgHero = findViewById(R.id.imgHero);
        imgEnemy = findViewById(R.id.imgEnemy) ;

        // set the text views upon startup
        txtHeroHP.setText(String.valueOf(heroHP + "/1000 HP")) ;
        txtHeroDamage.setText(String.valueOf(String.valueOf(heroMinDamage) + " DMG to\n" + String.valueOf(heroMaxDamage) + " DMG")) ;
        txtEnemyHP.setText(String.valueOf(enemyHP + "/3000 HP")) ;
        txtEnemyDamage.setText(String.valueOf(String.valueOf(enemyMinDamage) + " DMG to \n" + String.valueOf(enemyMaxDamage) + " DMG")) ;
        txtTurnCount.setText("Turn #" + String.valueOf(turnCount)) ;

        // setting listener for button
        btnNextTurn.setOnClickListener(this) ;
        btnHeal.setOnClickListener(this);
        btnIncreaseDMG.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //NEXT TURN BUTTON
        switch(v.getId()) {
            case R.id.btnNextTurn:

                /*hero's turn*/
                if(turnCount % 2 == 0) {

                    heroDamage = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMinDamage;

                    //for attacking
                    enemyHP = enemyHP - heroDamage ;
                    turnCount++ ;

                    //change image view of enemy
                    imgEnemy.setImageResource(R.drawable.enemy_hurt);
                    imgHero.setImageResource(R.drawable.hero_regular);

                    txtEnemyHP.setText(String.valueOf(enemyHP + "/3000 HP")) ;
                    txtTurnCount.setText("Turn #" + String.valueOf(turnCount)) ;
                    txtGameLog.setText("Hero deals " + String.valueOf(heroDamage) + "\ndamage to the enemy." ) ;
                    btnNextTurn.setText("Enemy's turn") ;

                    //Disable the Heal and attack increase button
                    btnHeal.setEnabled(false) ;
                    btnIncreaseDMG.setEnabled(false) ;
                    btnNewGame.setEnabled(false);

                    //Critical Hit condition message
                    if (heroDamageIncrease == false && heroDamage >=300) {
                        txtGameLog.setText(String.valueOf("Hero deals " + String.valueOf(heroDamage) + " damage \nto the enemy. A critical hit!")) ;
                    }

                    else if (heroDamageIncrease == true && heroDamage >= 650){
                        txtGameLog.setText(String.valueOf("Hero deals " + String.valueOf(heroDamage) + " damage \nto the enemy. A critical hit!")) ;
                    }

                    else {}


                    //Disabling Attack Increase after attacking and setting it back to regular damage range
                   if (heroDamageIncrease == true) {
                       heroDamageIncrease = false;
                       heroMinDamage = 150;
                       heroMaxDamage = 350;
                       txtHeroDamage.setText(String.valueOf(String.valueOf(heroMinDamage) + " DMG to\n" + String.valueOf(heroMaxDamage) + " DMG"));
                   }


                    //Win condition
                    if (enemyHP <= 0) {
                        txtGameLog.setText("Hero deals " + String.valueOf(heroDamage) + "\ndamage to the enemy. You Win!" ) ;
                        btnNextTurn.setText("Victory!") ;
                        //Reset variables
                        heroHP = 1000 ;
                        enemyHP = 3000 ;
                        turnCount = 0 ;
                        btnHeal.setEnabled(false) ;
                        btnIncreaseDMG.setEnabled(false) ;
                        btnNextTurn.setEnabled(false);
                        btnNewGame.setEnabled(true);

                    }
                    else {}

                }

                //Enemy's turn
                else if (turnCount % 2 == 1) {

                    enemyDamage = randomizer.nextInt(enemyMaxDamage - enemyMinDamage) + enemyMinDamage ;

                    //during attacking
                    heroHP = heroHP - enemyDamage ;
                    turnCount++ ;
                    txtHeroHP.setText(String.valueOf(heroHP + "/1000 HP")) ;
                    txtTurnCount.setText("Turn #" + String.valueOf(turnCount)) ;
                    txtGameLog.setText(String.valueOf("Enemy deals " + String.valueOf(enemyDamage) + "\ndamage to the hero.")) ;
                    btnNextTurn.setText("Hero's turn") ;

                    btnNewGame.setEnabled(false);

                    //Enable the Heal and attack increase button
                    btnHeal.setEnabled(true) ;
                    btnIncreaseDMG.setEnabled(true) ;

                    //change image view of hero
                    imgHero.setImageResource(R.drawable.hero_hurt);
                    imgEnemy.setImageResource(R.drawable.enemy_regular);

                    //Critical hit indicator
                    if (enemyDamage >= 200) {

                        txtGameLog.setText(String.valueOf("Enemy deals " + String.valueOf(enemyDamage) + " damage \nto the hero. A critical hit!")) ;

                    }

                    //Disabled attack increase button if attack is already boosted
                    if (heroDamageIncrease == true) {
                        btnIncreaseDMG.setEnabled(false);
                    }

                    else {}

                    //Lose Condition
                    if (heroHP <= 0) {
                        txtGameLog.setText(String.valueOf("Enemy deals " + String.valueOf(enemyDamage) + "\ndamage to the hero. You Lose!")) ;
                        btnNextTurn.setText("Defeat.") ;
                        btnHeal.setEnabled(false) ;
                        btnIncreaseDMG.setEnabled(false) ;
                        btnNextTurn.setEnabled(false);
                        btnNewGame.setEnabled(true);
                    }
                    else {}
                }

                break;
        }

        //HEAL BUTTON
        switch(v.getId()) {
            case R.id.btnHeal:

                //Hero Heal Randomizer
                int heroHeal = randomizer.nextInt(heroMaxHeal - heroMinHeal) + heroMinHeal;

                //Adds health to hero
                heroHP = heroHP + heroHeal;
                txtHeroHP.setText(String.valueOf(heroHP + "/1000 HP")) ;

                txtGameLog.setText("Hero heals " + heroHeal + " HP.");
                btnNextTurn.setText("Enemy's turn") ;

                turnCount++ ;
                txtTurnCount.setText("Turn #" + String.valueOf(turnCount)) ;

                //Disable Button
                btnHeal.setEnabled(false) ;
                btnIncreaseDMG.setEnabled(false) ;
                btnNewGame.setEnabled(false);

                //nobody's getting hurt
                imgEnemy.setImageResource(R.drawable.enemy_regular);
                imgHero.setImageResource(R.drawable.hero_regular);

                //Full recovery indicator
                if (heroHP == 1000) {
                    txtGameLog.setText("Hero heals " + heroHeal + " HP.\nA full recovery!");

                }

                else if (heroHP > 1000) {
                    txtGameLog.setText("Hero heals " + heroHeal + " HP.\nDivine recovery!");

                }

                else{}

        }

        //ATTACK INCREASE BUTTON
        switch(v.getId()) {
            case R.id.btnIncreaseDMG:
                // set actual increase
                heroMinDamage = 250;
                heroMaxDamage = 700;
                heroDamageIncrease = true ;

                txtHeroDamage.setText(String.valueOf(String.valueOf(heroMinDamage) + " DMG to\n" + String.valueOf(heroMaxDamage) + " DMG")) ;
                txtGameLog.setText("Hero has increased their\nattack damage!");
                btnNextTurn.setText("Enemy's turn") ;

                //nobody's getting hurt
                imgEnemy.setImageResource(R.drawable.enemy_regular);
                imgHero.setImageResource(R.drawable.hero_regular);

                //Disable Button
                btnHeal.setEnabled(false) ;
                btnIncreaseDMG.setEnabled(false) ;
                btnNewGame.setEnabled(false);

                turnCount++ ;
                txtTurnCount.setText("Turn #" + String.valueOf(turnCount)) ;

        }


        //RETRY BUTTON
        switch(v.getId()) {
            case R.id.btnNewGame:
                //resets everything
                btnNextTurn.setText("First turn") ;

                heroHP = 1000 ;
                enemyHP = 3000 ;
                turnCount = 0 ;
                heroDamageIncrease = false ;
                heroMinDamage = 150;
                heroMaxDamage = 350;

                txtHeroDamage.setText(String.valueOf(String.valueOf(heroMinDamage) + " DMG to\n" + String.valueOf(heroMaxDamage) + " DMG"));
                txtHeroHP.setText(String.valueOf(heroHP + "/1000 HP")) ;
                txtEnemyHP.setText(String.valueOf(enemyHP + "/3000 HP")) ;
                txtGameLog.setText(String.valueOf("Round Begins!")) ;
                txtTurnCount.setText("Turn #" + String.valueOf(turnCount)) ;

                //nobody's getting hurt
                imgEnemy.setImageResource(R.drawable.enemy_regular);
                imgHero.setImageResource(R.drawable.hero_regular);

                btnHeal.setEnabled(true) ;
                btnIncreaseDMG.setEnabled(true) ;
                btnNextTurn.setEnabled(true);

        }

    }

}