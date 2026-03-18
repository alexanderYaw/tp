package tradelog.logic.command;

import tradelog.exception.TradeLogException;
import tradelog.model.Trade;
import tradelog.model.TradeList;
import tradelog.storage.Storage;
import tradelog.ui.Ui;

/**
 * Represents a command to delete an existing trade from the TradeLog.
 * Handles parsing, strict validation of the trade index, and executing the deletion.
 */
public class DeleteCommand extends Command {
    private final int tradeIndex;

    /**
     * Constructs a DeleteCommand by parsing and validating the raw arguments string.
     * Strictly checks that the trade index is present and is a valid positive integer.
     *
     * @param arguments The raw string after the "delete" command word.
     * @throws TradeLogException If the trade index is missing, blank, or not a valid positive integer.
     */
    public DeleteCommand(String arguments) throws TradeLogException {
        arguments = arguments.trim();

        if (arguments.isEmpty()) {
            throw new TradeLogException("Missing trade index for delete command.");
        }

        try {
            tradeIndex = Integer.parseInt(arguments);
            
            if (tradeIndex <= 0) {
                throw new TradeLogException("Trade index must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new TradeLogException("Trade index must be a valid integer.");
        }
        
        // Invariant: tradeIndex must be positive
        assert tradeIndex > 0 : "Trade index should be positive";
    }
    @Override
    public void execute(TradeList tradeList, Ui ui, Storage storage) {
        try {
            int initialSize = tradeList.size();
            Trade deletedTrade = tradeList.deleteTrade(tradeIndex - 1);

            // Invariant: TradeList size should decrease by 1 after deletion
            assert tradeList.size() == initialSize - 1 : "TradeList size should decrease by 1 after deletion";
            // Invariant: Deleted trade should not be null
            assert deletedTrade != null : "Deleted trade should not be null";

            System.out.println(deletedTrade.toSummaryString());
            System.out.println("Trade successfully deleted.");

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Trade index does not exist!");
        }
    }
}
