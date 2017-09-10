package com.forhope.lamine.sophia;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private Button button1;
    private  Button button2;
    private FrameLayout mainPic;
    private TypeWriter tw;
    private int line=0;
    private int buttonText=1;
    private MediaPlayer mediaPlayer;
    private int[] bm = {R.raw.tenebrous,R.raw.thedescent,R.raw.nightmares};
    private int currentTrack = 0;
    private List<String> ButtonA ;
    private   List<String> ButtonB;
    private  MediaPlayer soundPlayer ;
    private  Button submitBtn;
    private  EditText typeText;
    private TextView instText;
    private final Handler handler = new Handler();
   //sql
    private String[] answersArray = {"Hello","World"};
    private int answersNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /////////////////////
        //fullscreen
        hideSystemUI();
        //
        UiChangeListener();
        //layout
        setContentView(R.layout.activity_main);
        //buttonTexts
        ButtonA = Arrays.asList(getResources().getStringArray(R.array.day1_button_a_array));
        ButtonB = Arrays.asList(getResources().getStringArray(R.array.day1_button_b_array));
        //init buttons
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        tw = (TypeWriter) findViewById(R.id.text);
        //
        submitBtn = (Button) findViewById(R.id.submit_btn);
        typeText = (EditText) findViewById(R.id.type_text);
        instText = (TextView) findViewById(R.id.instructions);

        //updown animation
        animation();
        //button click animation
        onButtonClick(button1);
        onButtonClick(button2);
        //background music
        playBM();
        //////////////////////
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateText(0);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateText(1);

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(typeText.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Type something",Toast.LENGTH_LONG).show();
                } else {
                verifyAnswer(typeText.getText().toString());
                }
            }
        });

    }




    @Override
    protected void onResume()
    {
        super.onResume();
        mediaPlayer.start();
        hideSystemUI();


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) //add a null check here
        {
            if (mediaPlayer.isPlaying()) {
             mediaPlayer.pause();
                // if this does nothing then the else isn't needed
            }
    }
    }

    @Override
    public void onStop()
    {

        super.onStop();
    }
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    public void UiChangeListener()
    {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
    }
     //button click
    private void onButtonClick(final Button btn) {
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    btn.setBackgroundResource(R.drawable.mybuttonpressed);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    btn.setBackgroundResource(R.drawable.mybutton);

                }
                return false;
            } });
    }

    //animation
    private void animation(){
        mainPic = (FrameLayout) findViewById(R.id.sophia);
        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        final  Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mainPic.startAnimation(slide_up);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };


        Animation.AnimationListener listener1 = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mainPic.startAnimation(slide_down);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        //Load animation

        slide_down.setAnimationListener(listener);
        slide_up.setAnimationListener(listener1);
        // Start animation
        mainPic.startAnimation(slide_down);
    }

    public void updateText(int p) {
        //Text Update

        typer(textStrings(line,p));
        soundEffects();

        //Button Update
    }
    private  void typer(String s) {
        //Text Effects
        tw.setText("");
        tw.setCharacterDelay(150);
        tw.animateText(s);
        //buttons invisible
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              if(!keepResponse()) {
                  animationT();
              } else {
                  handler.postDelayed(new Runnable() {
                      public void run() {
                          updateText(0);
                      }
                  }, 1000);

              }
            }
        }, 172*s.length());
        line++;
    }

    private void playBM() {
        mediaPlayer= MediaPlayer.create(getApplicationContext(), bm[currentTrack]);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentTrack+1 < bm.length) {
                    currentTrack++;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), bm[currentTrack]);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(this);
                } else {
                    currentTrack=0;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), bm[currentTrack]);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(this);
                }
            }
        });


    }

    private String textStrings(int l, int p) {
        List<String> LinesA = Arrays.asList(getResources().getStringArray(R.array.day1_array));
        List<String> LinesB = Arrays.asList(getResources().getStringArray(R.array.day1_arrayb));
        if(p==0){
            return LinesA.get(l);
        } else {
            return LinesB.get(l);
        }

    }
    //sound effects
    private void soundEffects(){

        switch (line) {
            case 1:
                soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.door);
                soundPlayer.start();
                break; case 2:
                soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.singing1);
                soundPlayer.start();
                break;
            case 7:
                soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.dolly);
                soundPlayer.start();
                break;
            case 20:
                soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.creep1);
                soundPlayer.start();
                soundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.scream1);
                        soundPlayer.start();
                        soundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.seeyou);
                                soundPlayer.start();
                            }
                        });
                    }
                });
                break;
            case 18:
                soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.laugh);
                soundPlayer.start();
                break;

        }


    }
    private void submitLayout(){
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        tw.setVisibility(View.INVISIBLE);
        tw.setText("");
        submitBtn.setVisibility(View.VISIBLE);
        instText.setVisibility(View.VISIBLE);
        typeText.setVisibility(View.VISIBLE);
        if(line == 3){
            instText.setText(textStrings(line-1,0));
            instText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
        }
    }
    private void revertLayout(){
        tw.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.INVISIBLE);
        instText.setVisibility(View.INVISIBLE);
        typeText.setVisibility(View.INVISIBLE);
    }
    private void verifyAnswer(String s) {
        if (s.matches(answersArray[answersNumber])) {
            updateText(0);
            revertLayout();
        }
    }
    //keep the text going
    public boolean keepResponse() {
        return line==8||line==9||line==12||line==13||line==15||line==16|| (line >= 20 && line<27) ;
    }
    private void animationT(){

        final Animation zoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        final  Animation zoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_out);
        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tw.startAnimation(zoomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        Animation.AnimationListener listener1 = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(!keepResponse()) {
                    if(line==3){
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                submitLayout();
                            }
                        }, 2000);

                    }else {
                     //   revertLayout();
                        button1.setText(ButtonA.get(buttonText));
                    button1.setVisibility(View.VISIBLE);
                    button2.setText(ButtonB.get(buttonText));
                    button2.setVisibility(View.VISIBLE);
                        tw.setVisibility(View.VISIBLE);

                }}
                buttonText++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        //Load animation

        zoomIn.setAnimationListener(listener);
        zoomOut.setAnimationListener(listener1);
        // Start animation
        tw.startAnimation(zoomIn);

    }


}
