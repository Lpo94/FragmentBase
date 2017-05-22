package com.example.pc.fragmentbase.Other;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.example.pc.fragmentbase.R;

import java.io.IOException;

/**
 * Created by SharkGaming on 03/05/2017.
 */

public class SoundManager
{
    private static SoundManager instance;
    private SoundPool soundPool;
    private int shoot = -1;
    public MediaPlayer baggroundMusic;


    public static SoundManager getInstance()
    {
        if(instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }
    private SoundManager()
    {
        super();
        baggroundMusic = new MediaPlayer();
    }

    public void loadSounds(Context _context)
    {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try
        {
            AssetManager assetManager = _context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("fireball.mp3");
            shoot = soundPool.load(descriptor, 0);

            // tilføj nye lyde her ved at genbruge de 2 ovenstående linjer med udskiftede variabler
        }

        catch(IOException e)
        {
            Log.e("error", "failed to load sound files");
        }
    }

    public void playSound(String _sound)
    {
        switch (_sound)
        {
            case "shoot":
                // id, leftVol, rightVol, priority(0 = lavest), loopmode(0=no loop, -1 = loop), playback rate
                soundPool.play(shoot, 1, 1, 0, 0, 1);
                break;
        }
    }

    public void startBackgroundMusic()
    {
                if(baggroundMusic == null)
                {
                    baggroundMusic = MediaPlayer.create(StaticValues.Instance().staticContext, R.raw.menu);
                    baggroundMusic.setLooping(true);
                    baggroundMusic.setVolume(0.05f, 0.05f);
                    baggroundMusic.start();
                }
    }
}
