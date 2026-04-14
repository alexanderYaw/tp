package tradelog.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tradelog.exception.TradeLogException;
import tradelog.model.ModeManager;
import tradelog.model.TradeList;
import tradelog.storage.Storage;
import tradelog.ui.Ui;

/**
 * Test suite for SetModeCommand, ensuring state transitions between LIVE and BACKTEST.
 */
public class SetModeCommandTest {

    private TradeList tradeList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        tradeList = new TradeList();
        ui = new Ui();
        storage = new Storage("dummy_set_mode_storage.txt");
        // Ensure starting state is always BACKTEST
        ModeManager.getInstance().setLive(false);
    }

    /**
     * Tests that providing 'live' correctly updates the ModeManager state.
     */
    @Test
    public void execute_validLiveMode_updatesToLive() throws TradeLogException {
        SetModeCommand command = new SetModeCommand("live");
        command.execute(tradeList, ui, storage);

        assertTrue(ModeManager.getInstance().isLive(), "Mode should be LIVE after command execution.");
        assertEquals(ModeManager.EnvironmentMode.LIVE, ModeManager.getInstance().getCurrentMode());
    }

    /**
     * Tests that providing 'backtest' correctly updates the ModeManager state.
     */
    @Test
    public void execute_validBacktestMode_updatesToBacktest() throws TradeLogException {
        // Manually switch to live first
        ModeManager.getInstance().setLive(true);

        SetModeCommand command = new SetModeCommand("backtest");
        command.execute(tradeList, ui, storage);

        assertFalse(ModeManager.getInstance().isLive(), "Mode should be BACKTEST after command execution.");
        assertEquals(ModeManager.EnvironmentMode.BACKTEST, ModeManager.getInstance().getCurrentMode());
    }

    /**
     * Tests that the command is case-insensitive (e.g., 'LIVE' should work).
     */
    @Test
    public void execute_caseInsensitiveInput_updatesCorrectly() throws TradeLogException {
        SetModeCommand command = new SetModeCommand("LiVe");
        command.execute(tradeList, ui, storage);

        assertTrue(ModeManager.getInstance().isLive());
    }

    /**
     * Tests that an invalid mode string throws a TradeLogException.
     */
    @Test
    public void constructor_invalidMode_throwsTradeLogException() {
        assertThrows(TradeLogException.class, () -> new SetModeCommand("invalid_mode"));
    }

    /**
     * Tests that empty or blank arguments throw a TradeLogException.
     */
    @Test
    public void constructor_emptyArgs_throwsTradeLogException() {
        assertThrows(TradeLogException.class, () -> new SetModeCommand(""));
        assertThrows(TradeLogException.class, () -> new SetModeCommand("   "));
    }

    /**
     * Verifies that SetModeCommand is not an exit command.
     */
    @Test
    public void isExit_returnsFalse() throws TradeLogException {
        assertFalse(new SetModeCommand("live").isExit());
    }
}
