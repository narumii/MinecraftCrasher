package uwu.narumi.crasher.core.command;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import uwu.narumi.crasher.api.command.Command;
import uwu.narumi.crasher.api.command.CommandInfo;
import uwu.narumi.crasher.api.exception.CommandException;
import uwu.narumi.crasher.api.exploit.Exploit;
import uwu.narumi.crasher.api.exploit.argument.ArgumentParser;
import uwu.narumi.crasher.api.helper.ExploitHelper;
import uwu.narumi.crasher.core.Crasher;

@CommandInfo(
    alias = "exploit",
    description = "Attacks server",
    usage = "exploit <args>",
    aliases = {"crash"}
)
public class CrashCommand extends Command {

  @Override
  public void execute(String... args) throws CommandException {
    if (args.length == 0) {
      throw new CommandException("Usage: " + getUsage());
    }

    if (args[0].equalsIgnoreCase("list")) {
      System.out.println(
          "Available methods: " + Crasher.INSTANCE.getExploitManager().getExploits().stream()
              .map(Exploit::getName).collect(Collectors.joining(", ")));
    } else if (args[0].equalsIgnoreCase("info") && args.length > 1) {
      Crasher.INSTANCE.getExploitManager().getExploit(args[1])
          .ifPresentOrElse(exploit -> System.out
                  .println(String.format("%s: %s\n", exploit.getName(), exploit.getDescription())),
              () -> System.out.println(String.format("Exploit \"%s\" not found.\n", args[0])));
    } else {
      Optional<Exploit<?>> exploit = Crasher.INSTANCE.getExploitManager().getExploit(args[0]);
      if (exploit.isPresent()) {
        ExploitHelper.submit(exploit.get(), ArgumentParser.parseArgs(exploit.get(), Arrays.copyOfRange(args, 1, args.length)));
        return;
      }

      throw new CommandException("Exploit not found. Use \"exploit list\" to see all exploits.");
    }
  }
}
