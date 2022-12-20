/*
 * ISO code resolver: Picocli based sample application (with subcommands)
 * Explanation: <a href="https://picocli.info/quick-guide.html#_subcommands_example_iso_code_resolver">Picocli quick guide</a>
 * Source Code: <a href="https://github.com/remkop/picocli/blob/master/picocli-examples/src/main/java/picocli/examples/subcommands/SubcommandDemo.java">GitHub</a>
 * @author Andreas Deininger
 */

package picocli.demo;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

import java.util.Locale;

// ISOCodeResolver language de cs en sd se
// ISOCodeResolver country cn fr th ro no
@Command(name = "ISOCodeResolver", subcommands = {SubcommandAsClass.class, CommandLine.HelpCommand.class}, // |2|
        description = "Resolve ISO country codes (ISO-3166-1) or language codes (ISO 639-1 or -2)")
public class ISOCodeResolver implements Runnable { // |1|
    @Spec
    CommandSpec spec;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ISOCodeResolver()).execute(args); // |5|
        System.exit(exitCode); // |6|
    }

    @Command(name = "country", description = "Resolve ISO country code (ISO-3166-1, Alpha-2 code)")
        // |3|
    void subCommandViaMethod(@Parameters(arity = "1..*", paramLabel = "<country code>",
            description = "country code(s) to be resolved") String[] countryCodes) {
        for (String code : countryCodes) {
            System.out.printf("%s: %s%n", code.toUpperCase(), new Locale("", code).getDisplayCountry());
        }
    }

    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    }
}

@Command(name = "language", description = "Resolve ISO language code (ISO 639-1 or -2, two/three letters)") // |4|
class SubcommandAsClass implements Runnable {

    @Parameters(arity = "1..*", paramLabel = "<language code>", description = "language code(s) to be resolved")
    private String[] languageCodes;

    @Override
    public void run() {
        for (String code : languageCodes) {
            System.out.printf("%s: %s%n", code.toLowerCase(), new Locale(code).getDisplayLanguage());
        }
    }
}
