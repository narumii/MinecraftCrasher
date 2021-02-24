package uwu.narumi.crasher.api.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Executors;
import uwu.narumi.crasher.api.exception.CommandException;

public class CommandManager {

  private static final String PREFIX = ".";

  private final List<Command> commands;

  public CommandManager(Command... commands) {
    this.commands = Arrays.asList(commands);
  }

  public void startHandlingCommands() {
    Executors.newSingleThreadExecutor().execute(()-> {
      Scanner scanner = new Scanner(System.in);
      try {
        while (true) { //you can use scanner.hasNext() / scanner.hasNextLine() but i want command line prefix xd
          Thread.sleep(10); //anti cpu burner
          System.out.append("mcc > \r");
          handleCommand(scanner.nextLine());
        }
      }catch (Exception e) {}
    });
  }

  private void handleCommand(String message) {
    if (message.isBlank() || message.isEmpty()) {
      return;
    }

    String[] args = message.split(" ");
    getCommand(args[0]).ifPresentOrElse(command -> {
      try {
        command.execute(Arrays.copyOfRange(args, 1, args.length));
      } catch (CommandException e) {
        System.out.println(e.getMessage());
      }
    }, () -> System.out.printf("Command \"%s\" not found. Use \"help\" to see command list.%n", args[0]));
  }

  public void registerCommand(Command command) {
    this.commands.add(command);
  }

  public void registerCommands(Command... commands) {
    this.commands.addAll(Arrays.asList(commands));
  }

  public void unregisterCommand(Command command) {
    this.commands.remove(command);
  }

  public void unregisterCommands(Command... commands) {
    this.commands.removeAll(Arrays.asList(commands));
  }

  public Optional<Command> getCommand(String alias) {
    return commands.stream().filter(command -> command.is(alias)).findFirst();
  }

  public List<Command> getCommands() {
    return commands;
  }

  public static String getPrefix() {
    return PREFIX;
  }
}
