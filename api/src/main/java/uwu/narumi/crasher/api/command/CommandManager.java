package uwu.narumi.crasher.api.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import uwu.narumi.crasher.api.exception.CommandException;
import uwu.narumi.crasher.api.optimizer.Optimizer;

public class CommandManager implements Completer {

  private static final String PREFIX = ".";

  private final List<Command> commands;
  private Terminal terminal;
  private LineReader lineReader;
  private ExecutorService executorService = Executors.newSingleThreadExecutor();

  public CommandManager(Command... commands) {
    try {
      terminal = TerminalBuilder.builder().system(true).streams(System.in, System.out).build();
      lineReader = LineReaderBuilder.builder().terminal(terminal).completer(this).build();

      executorService.submit(()-> {
        String line;
        while ((line = lineReader.readLine("mcc > ")) != null) {
          handleCommand(line);
        }
      });
    }catch (Exception e) {
      executorService.submit(()-> {
        Scanner scanner = new Scanner(System.in);
        while (true) {
          Thread.sleep(10);
          handleCommand(scanner.nextLine());
        }
      });
    }
    this.commands = Arrays.asList(commands);
  }

  private void handleCommand(String message) {
    if (message.isBlank() || message.isEmpty()) {
      Optimizer.stopOptimizing();
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

  @Override
  public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
    if (parsedLine.line().isEmpty() || parsedLine.line().isBlank()) {
      list.addAll(commands.stream()
          .map(command -> new Candidate(command.getAlias()))
          .collect(Collectors.toList())
      );
    } else {
      list.addAll(commands.stream()
          .filter(command -> command.getAlias().startsWith(parsedLine.line()))
          .map(command -> new Candidate(command.getAlias()))
          .collect(Collectors.toList())
      );
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
