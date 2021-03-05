package com.drastic.plugin.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class ScoreboardManager
{
    public Player player;
    public FastBoard scoreboardSign;
    public static Map<Player, FastBoard> scoreboardGame = new HashMap<Player, FastBoard>();

    public ScoreboardManager(Player player)
    {
        this.player = player;
        this.scoreboardSign = new FastBoard(player);
        scoreboardGame.put(player, this.scoreboardSign);
    }
}