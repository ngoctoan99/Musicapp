package com.example.mymedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtTitle, txtTimeSong, txtTimeEnd;
    SeekBar skSong;
    ImageButton btnPlay, btnStop, btnPre, btnNext;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    ImageView imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddSong();
        AddMediaPlayer();
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    AddMediaPlayer();
                    btnPlay.setImageResource(R.drawable.play_s);
                }
                else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_s);
                    SetTimeEnd();
                    UpdateTimeSong();
                    imgs.startAnimation(animation);
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play_s);
                AddMediaPlayer();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position >= arraySong.size()) {
                    position = 0;
                }
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                AddMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_s);
                SetTimeEnd();
                UpdateTimeSong();
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < 0) {
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                AddMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_s);
                SetTimeEnd();
                UpdateTimeSong();
            }
        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }
    private void UpdateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position >= arraySong.size()) {
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        AddMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause_s);
                        SetTimeEnd();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }
    private void SetTimeEnd() {
        SimpleDateFormat dinhdang =  new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(dinhdang.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }
    private void AddMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }
    private void AddSong() {

        arraySong = new ArrayList<>();
        arraySong.add(new Song("Anh không tha thứ ",R.raw.anhkhongthathu));
        arraySong.add(new Song("Cô gái vàng - HuyR",R.raw.cogaivang));
        arraySong.add(new Song("Nàng Thơ",R.raw.nangtho));
        arraySong.add(new Song("Anh thương em còn non dại",R.raw.anhthuongemconnondai));

    }
    private void AnhXa() {
        txtTimeSong  = (TextView) findViewById(R.id.tv_first);
        txtTimeEnd   = (TextView) findViewById(R.id.tv_second);
        skSong       = (SeekBar) findViewById(R.id.sb);
        txtTitle     = (TextView) findViewById(R.id.tv_song);
        btnPlay      = (ImageButton) findViewById(R.id.image_play);
        btnNext      = (ImageButton) findViewById(R.id.image_next);
        btnPre       = (ImageButton) findViewById(R.id.image_pre);
        btnStop      = (ImageButton) findViewById(R.id.image_pause);
        imgs         = (ImageView) findViewById(R.id.imageView);
    }
}