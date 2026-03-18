package tradelog.logic.command;

import java.util.logging.Level;
import java.util.logging.Logger;

import tradelog.model.TradeList;
import tradelog.storage.Storage;
import tradelog.ui.Ui;

/**
 * Command to display all logged trades.
 * Corresponds to the user command: {@code list}
 */
public class ListCommand extends Command {

    /** Logger for tracking command execution. */
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    /**
     * Executes the list command by printing all trades via the UI.
     *
     * @param tradeList The current list of trades.
     * @param ui        The UI handler used to print the trade list.
     * @param storage   Not used by this command.
     */
    @Override
    public void execute(TradeList tradeList, Ui ui, Storage storage) {
        assert tradeList != null : "TradeList should not be null";
        assert ui != null : "Ui should not be null";

        logger.log(Level.INFO, "Executing list command. Trade count: {0}", tradeList.size());

        ui.printTradeList(tradeList);

        logger.log(Level.INFO, "List command executed successfully.");
    }
}
