package todolist.utils;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(record.getLevel()).append(": ");
        builder.append(formatMessage(record)).append("\n");
        return builder.toString();
    }
}
