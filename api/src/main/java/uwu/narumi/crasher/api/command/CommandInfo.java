package uwu.narumi.crasher.api.command;

import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

  String alias();

  String description() default "No description provided";

  String usage() default "Command doesn't have usage";

  String[] aliases() default {};
}
