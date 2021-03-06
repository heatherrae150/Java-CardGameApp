package com.example.heather.blackjack.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heather.blackjack.Dealers.Dealer;
import com.example.heather.blackjack.Dealers.TestDealer;
import com.example.heather.blackjack.Games.BlackJackGame;
import com.example.heather.blackjack.Players.BlackJackPlayer;
import com.example.heather.blackjack.R;

public class PlayBlackJackActivity extends AppCompatActivity {

    TextView player1NameText;
    TextView player2NameText;

    TextView player1Card1;
    TextView player1Card2;
    TextView player1Card3;
    TextView player1Card4;
    TextView player1Card5;

    TextView player2Card1;
    TextView player2Card2;
    TextView player2Card3;
    TextView player2Card4;
    TextView player2Card5;

    TextView informationText;

    Button player1StickButton;
    Button player1HitButton;
    Button playAgainButton;
    Button chooseAnotherGameButton;

    Dealer blackJackDealer;
    BlackJackGame blackJackGame;
    BlackJackPlayer player1;
    BlackJackPlayer player2;

    Handler setDelay;
    Runnable startDelay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_black_jack);

        getViews();

        blackJackDealer = new TestDealer();
        blackJackGame = new BlackJackGame(blackJackDealer);

        setDelay = new Handler();



//          sets players names
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String player1Name = extras.getString("player1Name");
        String setPlayer1Name = player1Name;

        String player2Name = "DEALER";
        String setPlayer2Name = player2Name;

        player1NameText.setText(setPlayer1Name);
        player2NameText.setText(setPlayer2Name);

        player1 = new BlackJackPlayer(setPlayer1Name);
        player2 = new BlackJackPlayer(setPlayer2Name);



//          set up game
        blackJackDealer.createDeck();
        blackJackDealer.shuffleDeck();

        blackJackGame.addPlayer(player1);
        blackJackGame.addPlayer(player2);

        blackJackGame.dealRound();

        player1Card1.setText(player1.getHand().get(0).getShortName());
        player1Card2.setText(player1.getHand().get(1).getShortName());

        player2Card1.setText(player2.getHand().get(0).getShortName());
        player2Card2.setText("???");


//        declare winner if blackjack present
        if (blackJackGame.hasBlackJack(player1) == true) {
            makeToast("PLAYER 1 HAS BLACKJACK", Toast.LENGTH_SHORT);

            startDelay = new Runnable() {
                @Override
                public void run() {
                    player2Card2.setText(player2.getHand().get(1).getShortName());
                    if (blackJackGame.hasBlackJack(player2) == true) {
                        makeToast(player2.getName().toUpperCase() + " HAS BLACKJACK", Toast.LENGTH_SHORT);
                        informationText.setText(blackJackGame.declareWinnerWithBlackJack(player2));
                    } else
                        informationText.setText(blackJackGame.declareWinnerWithBlackJack(player1));
                }
            };
            setDelay.postDelayed(startDelay, 5000);
        } else
            informationText.setText(player1.getName().toUpperCase() + " - STICK OR HIT???");
    }


    public void getViews() {
        player1NameText = (TextView) findViewById(R.id.player1_name);
        player2NameText = (TextView) findViewById(R.id.player2_name);

        player1Card1 = (TextView) findViewById(R.id.player1_card1);
        player1Card2 = (TextView) findViewById(R.id.player1_card2);
        player1Card3 = (TextView) findViewById(R.id.player1_card3);
        player1Card4 = (TextView) findViewById(R.id.player1_card4);
        player1Card5 = (TextView) findViewById(R.id.player1_card5);

        player2Card1 = (TextView) findViewById(R.id.player2_card1);
        player2Card2 = (TextView) findViewById(R.id.player2_card2);
        player2Card3 = (TextView) findViewById(R.id.player2_card3);
        player2Card4 = (TextView) findViewById(R.id.player2_card4);
        player2Card5 = (TextView) findViewById(R.id.player2_card5);

        informationText = (TextView) findViewById(R.id.information_text);

        player1StickButton = (Button) findViewById(R.id.player1_stick_button);
        player1HitButton = (Button) findViewById(R.id.player1_hit_button);

        playAgainButton = (Button) findViewById(R.id.play_again_button);
        chooseAnotherGameButton = (Button) findViewById(R.id.choose_another_game_button);
    }


    public void onPlayer1HitButtonClicked(View view) {
        if (player1.getScore() < 21) {
            blackJackGame.dealCard(player1);

            if (player1.getHand().size() == 3) {
                player1Card3.setText(player1.getHand().get(2).getShortName());
                if (player1.getScore() > 21) {
                    makeToast(player1.getName().toUpperCase() + " BUST", Toast.LENGTH_SHORT);
                    informationText.setText("BUST, " + player2.getName().toUpperCase() + " WINS");
                    player1HitButton.setEnabled(false);
                    player1StickButton.setEnabled(false);
                } else
                    informationText.setText(player1.getName().toUpperCase() + " - STICK OR HIT???");

            } else if (player1.getHand().size() == 4) {
                player1Card4.setText(player1.getHand().get(3).getShortName());
                if (player1.getScore() > 21) {
                    makeToast(player1.getName().toUpperCase() + " BUST", Toast.LENGTH_SHORT);
                    informationText.setText("BUST, " + player2.getName().toUpperCase() + " WINS");
                    player1HitButton.setEnabled(false);
                    player1StickButton.setEnabled(false);
                } else
                    informationText.setText(player1.getName().toUpperCase() + " - STICK OR HIT???");

            } else if (player1.getHand().size() == 5) {
                player1Card5.setText(player1.getHand().get(4).getShortName());
                if (player1.getScore() > 21) {
                    makeToast(player1.getName().toUpperCase() + " BUST", Toast.LENGTH_SHORT);
                    informationText.setText("BUST, " + player2.getName().toUpperCase() + " WINS");
                    player1HitButton.setEnabled(false);
                    player1StickButton.setEnabled(false);
                } else
                    informationText.setText("WOW - 5 CARD TRICK - PLEASE STICK");
            }
        }
    }


    public void makeToast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }


    public void onPlayer1StickButtonClicked(View view) {
        setDelay = new Handler();

        makeToast(player1.getName().toUpperCase() + ", YOUR SCORE IS " + player1.getScore() + "!" , Toast.LENGTH_SHORT);
        player1HitButton.setEnabled(false);

        informationText.setText(player1.getName().toUpperCase() + " STICKS AT " + player1.getScore() );


        startDelay = new Runnable() {
            @Override
            public void run() {
                player2Card2.setText(player2.getHand().get(1).getShortName());
                if (blackJackGame.hasBlackJack(player2)) {
                    informationText.setText(blackJackGame.declareWinnerWithBlackJack(player2));
                }
            }
        };
        setDelay.postDelayed(startDelay, 2500);


        startDelay = new Runnable() {
            @Override
            public void run() {
                while (player2.getScore() < 17) {
                    blackJackGame.dealCard(player2);

                        if (player2.getHand().size() == 3) {
                            player2Card3.setText(player2.getHand().get(2).getShortName());
                            if (player2.getScore() > 21) {
                                makeToast(player2.getName().toUpperCase() + " BUST", Toast.LENGTH_SHORT);
                                informationText.setText("DEALER BUST, " + player1.getName().toUpperCase() + " WINS");
                            }

                        } else if (player2.getHand().size() == 4) {
                            player2Card4.setText(player2.getHand().get(3).getShortName());
                            if (player2.getScore() > 21) {
                                makeToast(player2.getName().toUpperCase() + " BUST", Toast.LENGTH_SHORT);
                                informationText.setText("DEALER BUST, " + player1.getName().toUpperCase() + " WINS");
                            }

                        } else if (player2.getHand().size() == 5) {
                            player2Card5.setText(player2.getHand().get(4).getShortName());
                            if (player2.getScore() > 21) {
                                makeToast(player2.getName().toUpperCase() + " BUST", Toast.LENGTH_SHORT);
                                informationText.setText("DEALER BUST, " + player1.getName().toUpperCase() + " WINS");
                            }

                        } else
                            informationText.setText("WOW - 5 CARD TRICK - DEALER STICKS");

                }

                if (player2.getScore() <= 21) {
                    Log.d("dealer wins", "does this log ever run???");
                    makeToast("DEALER'S SCORE IS " + player2.getScore() + "!", Toast.LENGTH_SHORT);
                    informationText.setText(blackJackGame.findWinner());
                }
            }
        };
        setDelay.postDelayed(startDelay, 4000);
    }


    public void onPlayAgainButtonClicked(View view) {
        Intent intent = new Intent(this, PlayerNamesBlackJackActivity.class);
        startActivity(intent);
    }

    public void onChooseAnotherGameButtonClicked(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


}
