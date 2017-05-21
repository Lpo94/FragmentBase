package com.example.pc.fragmentbase.Other;

/**
 * Created by SharkGaming on 16/05/2017.
 */

public interface iCollectable
{
    boolean canCollect(Player _player);
    void collect(Player _player);
}
