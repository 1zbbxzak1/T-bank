package backend.academy.log.parser.interfaces;

import backend.academy.log.records.LogRecord;

public interface ILogParser {
    LogRecord parse(String line);
}
