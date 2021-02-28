package uwu.narumi.crasher.core.command;

import uwu.narumi.crasher.api.command.Command;
import uwu.narumi.crasher.api.command.CommandInfo;
import uwu.narumi.crasher.api.exception.CommandException;
import uwu.narumi.crasher.api.optimizer.Optimizer;

@CommandInfo(
    alias = "stop",
    description = "Stop crashing",
    usage = "stop",
    aliases = {"s"}
)
public class StopCommand extends Command {

  @Override
  public void execute(String... args) throws CommandException {
    System.out.println("Stopping");
    Optimizer.stopOptimizing();
    System.out.println("Stopped");
  }
}
