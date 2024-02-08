import stdlib.StdDraw;
import stdlib.StdOut;

public final class SuperPacManApp {

    public static final String APPLICATION_TITLE = "Super Pac-Man";

    private static final String MODE_SERVER = "server";
    private static final String MODE_DRAWINGS = "drawings";

    private SuperPacManApp() {
    }

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


    private static void usage() {
        System.err.println("Invalid usage : java " + SuperPacManApp.class.getSimpleName());
        System.exit(-1);
    }

    private static void server() {
        StdOut.println("Server mode is started !");

    }


    private static void client() {

    }

    private static void single() {

    }

    private static void drawings() {

    }

    private static void game() {
        StdOut.println("Game mode is started !");

        StdDraw.setCanvasSize(800, 800);
    }


    public static void main(String[] args) {

        StdOut.println(LOGO);
        StdOut.println(APPLICATION_TITLE + " v" + VERSION + " started !");
        StdDraw.setTitle(APPLICATION_TITLE);
        StdDraw.setVisible(false);

        StdDraw.setCanvasSize(800, 800);
        //StdDraw.show();

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