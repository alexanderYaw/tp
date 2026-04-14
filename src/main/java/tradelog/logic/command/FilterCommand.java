package tradelog.logic.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import tradelog.logic.parser.ArgumentTokeniser;
import tradelog.model.ModeManager;
import tradelog.model.Trade;
import tradelog.model.TradeList;
import tradelog.storage.Storage;
import tradelog.ui.Ui;

/**
 * Represents a command to filter and display trades based on specific criteria.
 */
public class FilterCommand extends Command {

    private static final String[] ACCEPTED_PREFIXES = {"t/", "dir/", "strat/"};
    private static final Logger logger = Logger.getLogger(FilterCommand.class.getName());
    private final Map<String, String> filterArgs;

    /**
     * Constructs a FilterCommand by tokenising the user arguments.
     *
     * @param arguments The raw string containing filter prefixes.
     */
    public FilterCommand(String arguments) {
        assert arguments != null : "Arguments should not be null";
        this.filterArgs = ArgumentTokeniser.tokenise(arguments, ACCEPTED_PREFIXES);
    }

    /**
     * Executes the filter command.
     *
     * @param tradeList The current list of trades.
     * @param ui        The UI handler for output.
     * @param storage   The storage handler for persistence.
     */
    @Override
    public void execute(TradeList tradeList, Ui ui, Storage storage) {
        assert tradeList != null : "TradeList should not be null when executing filter";
        assert ui != null : "Ui should not be null when executing filter";

        if (tradeList.isEmpty()) {
            ui.showSummaryEmpty();
            return;
        }

        List<Integer> matchedIndices = new ArrayList<>();
        for (int i = 0; i < tradeList.size(); i++) {
            Trade trade = tradeList.getTrade(i);
            assert trade != null : "Trade at index " + i + " should not be null";

            if (isMatch(trade)) {
                matchedIndices.add(i);
            }
        }

        // obtain ModeManager instance
        ModeManager modeManager = ModeManager.getInstance();

        assert modeManager != null : "ModeManager should be initialized before checking isLive";

        if (matchedIndices.isEmpty()) {
            ui.showMessage("No trades found matching the criteria.");
            return;
        }

        // give instruction only in LIVE mode
        if (modeManager.isLive()) {
            ui.showMessage("[LIVE Mode Active] Note: Historical trades below are read-only.");
        }

        logger.log(Level.INFO, "Filter results found: {0} trades", matchedIndices.size());

        // invoke original print method
        ui.printIndexedTrades(tradeList, matchedIndices);
    }

    private boolean isMatch(Trade trade) {
        if (filterArgs.containsKey("t/") &&
                !trade.getTicker().equalsIgnoreCase(filterArgs.get("t/").trim())) {
            return false;
        }
        if (filterArgs.containsKey("dir/") &&
                !trade.getDirection().equalsIgnoreCase(filterArgs.get("dir/").trim())) {
            return false;
        }
        if (filterArgs.containsKey("strat/") &&
                !trade.getStrategy().equalsIgnoreCase(filterArgs.get("strat/").trim())) {
            return false;
        }
        return true;
    }
}
