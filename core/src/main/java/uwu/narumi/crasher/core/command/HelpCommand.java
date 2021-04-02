package uwu.narumi.crasher.core.command;

import uwu.narumi.crasher.api.command.Command;
import uwu.narumi.crasher.api.command.CommandInfo;
import uwu.narumi.crasher.api.exception.CommandException;
import uwu.narumi.crasher.core.Crasher;

@CommandInfo(
    alias = "help",
    aliases = {"commands"}
)
public class HelpCommand extends Command {

  @Override
  public void execute(String... args) throws CommandException {
    if (args.length > 0) {
      Command command = Crasher.INSTANCE.getCommandManager().getCommand(args[0])
          .orElseThrow(() -> new CommandException(""));
      System.out.printf("%s: %s - (%s)%n", command.getAlias(), command.getDescription(),
          command.getUsage());
      return;
    }

    Crasher.INSTANCE.getCommandManager().getCommands().stream()
        .filter(command -> !(command instanceof HelpCommand))
        .forEach(command ->
            System.out.println(String
                .format("%s: %s - (%s)", command.getAlias(), command.getDescription(),
                    command.getUsage())));
  }
}
