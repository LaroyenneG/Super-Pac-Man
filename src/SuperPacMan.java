import engine.GameEngine;
import model.Game;
import model.Player;
import stdlib.StdOut;

import java.awt.*;

public final class SuperPacMan {
    public static final String APPLICATION_TITLE = "Super Pac-Man";
    private static final String VERSION = "0.0.0";
    private static final int GRID_SIZE = 30;
    private static final String LOGO = """
                                    .#@@@@@@&,                \s
                            .@@&,.................#@@(        \s
                        *@&............................(@%    \s
                     /@,...................................@& \s
                   @@......................................*@*\s
                 &@......................................@&   \s
                @......................@@@@@..........&@      \s
               @.......................@@@@@/....../@,        \s
              @..................................@&           \s
             @/...............................@@              \s
             @............................./@.                \s
            *@...........................@%                   \s
            .@............................@&                  \s
             @..............................&@                \s
             *@...............................(@              \s
              &#................................,@,           \s
               &&..................................@#         \s
                .@...................................@@       \s
                  &@...................................#@     \s
                    #@...................................,@.  \s
                       @@................................@@   \s
                          ,@@*......................,@@(      \s
                                %@@@@#,,,,,,(@@@@@            \s
            """;


    private SuperPacMan() {
    }

    private static String myUsername() {

        var result = "?";

        var username = System.getProperty("user.name");
        if (username != null) {
            username = username.trim().toLowerCase();
            if (!username.isEmpty()) {
                result = username.substring(0, Math.min(5, username.length()));
            }
        }

        return result;
    }

    private static void usage() {
        System.err.println("Invalid usage : java " + SuperPacMan.class.getSimpleName());
        System.exit(-1);
    }


    private static void game() {
        StdOut.println("Game is starting...");

        var game = new Game(GRID_SIZE, new Player(myUsername()) /*, new Player("Bot", Color.WHITE)*/);

        var gameEngine = new GameEngine(game);

        gameEngine.start();

        StdOut.println("Game started");
    }


    public static void main(String[] args) {

        StdOut.println(LOGO);
        StdOut.println(APPLICATION_TITLE + " v" + VERSION + " started !");

        if (args.length == 0) {
            game();
        } else {
            usage();
        }
    }
}