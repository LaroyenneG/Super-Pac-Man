import engine.GameEngine;
import model.game.Game;
import stdlib.StdOut;

public final class SuperPacMan {
    public static final String APPLICATION_TITLE = "Super Pac-Man";
    private static final String MODE_SERVER = "server";
    private static final String MODE_DRAWINGS = "drawings";
    private static final String MODE_TESTS = "tests";
    private static final String VERSION = "0.0.0";
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

    private static void usage() {
        System.err.println("Invalid usage : java " + SuperPacMan.class.getSimpleName());
        System.exit(-1);
    }

    private static void server() {
        StdOut.println("Server mode is started !");
    }

    private static void tests() {

    }


    private static void client() {

    }

    private static void single() {

    }

    private static void drawings() {

    }

    private static void game() {
        StdOut.println("Game mode selected");

        var game = new Game(30);

        var gameEngine = new GameEngine(game);

        gameEngine.start();
    }


    public static void main(String[] args) {

        StdOut.println(LOGO);
        StdOut.println(APPLICATION_TITLE + " v" + VERSION + " started !");

        switch (args.length) {
            case 0:
                game();
                break;
            case 1:
                switch (args[0]) {
                    case MODE_SERVER:
                        server();
                        break;
                    case MODE_DRAWINGS:
                        drawings();
                        break;
                    case MODE_TESTS:
                        tests();
                        break;
                    default:
                        usage();
                }
                break;
            default:
                usage();
                break;
        }
    }
}