package tradelog.logic.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tradelog.exception.TradeLogException;
import tradelog.logic.command.CompareCommand;
import tradelog.logic.command.SetModeCommand; // Added import
import tradelog.model.ModeManager; // Added import

public class ParserTest {

    @BeforeEach
    public void setUp() {
        // Reset ModeManager to BACKTEST before each test for consistency
        ModeManager.getInstance().setLive(false);
    }

    @Test
    public void parseCommand_compare_returnsCompareCommand() throws TradeLogException {
        assertInstanceOf(CompareCommand.class, Parser.parseCommand("compare"));
    }

    /**
     * Verifies that the parser correctly identifies and creates a SetModeCommand.
     */
    @Test
    public void parseCommand_setmode_returnsSetModeCommand() throws TradeLogException {
        assertInstanceOf(SetModeCommand.class, Parser.parseCommand("setmode live"));
    }
}
