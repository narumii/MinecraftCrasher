package uwu.narumi.crasher.core.command;

import uwu.narumi.crasher.api.command.Command;
import uwu.narumi.crasher.api.command.CommandInfo;
import uwu.narumi.crasher.api.exception.CommandException;
import uwu.narumi.crasher.api.helper.ExploitHelper;

@CommandInfo(
    alias = "renew",
    description = "Renew previous attack",
    aliases = {"r"}
)
public class RenewCommand extends Command {

  @Override
  public void execute(String... args) throws CommandException {
    if (ExploitHelper.getLastExploit() != null && ExploitHelper.getLastArgs() != null) {
      ExploitHelper.submit(ExploitHelper.getLastExploit(), ExploitHelper.getLastArgs());
    }
  }
}
