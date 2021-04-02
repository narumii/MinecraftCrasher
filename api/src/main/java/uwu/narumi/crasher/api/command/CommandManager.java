package uwu.narumi.crasher.api.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import uwu.narumi.crasher.api.exception.CommandException;

public class CommandManager {

  private static final String PREFIX = ".";
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final List<Command> commands;

  public CommandManager(Command... commands) {
    startConsoleReading();
    this.commands = Arrays.asList(commands);
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
    }, () -> System.out
        .printf("Command \"%s\" not found. Use \"help\" to see command list.%n", args[0]));
  }

  private void startConsoleReading() {
    try {
      Terminal terminal = TerminalBuilder.builder().system(true).streams(System.in, System.out)
          .build();
      LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

      executorService.submit(() -> {
        String line;
        while ((line = lineReader.readLine("mcc > ")) != null) {
          handleCommand(line);
        }
      });
    } catch (Exception e) {
      executorService.submit(() -> {
        Scanner scanner = new Scanner(System.in);
        while (true) {
          Thread.sleep(10);
          System.out.print("mcc > ");
          handleCommand(scanner.nextLine());
        }
      });
    }
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
