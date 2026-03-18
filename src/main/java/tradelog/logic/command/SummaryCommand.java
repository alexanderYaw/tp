package tradelog.logic.command;

import tradelog.model.Trade;
import tradelog.model.TradeList;
import tradelog.storage.Storage;
import tradelog.ui.Ui;

/**
 * Represents a command to calculate and view the overall performance summary.
 * Handles both the mathematical calculations of the TradeList and the UI output.
 */
public class SummaryCommand extends Command {
    /**
     * Executes the summary command by calculating metrics directly from the TradeList
     * and displaying the formatted performance report.
     *
     * @param tradeList The current list of trades to analyze.
     * @param ui        The UI handler for output.
     * @param storage   The storage handler for persistence.
     */
    @Override
    public void execute(TradeList tradeList, Ui ui, Storage storage) {
        if (tradeList.isEmpty()) {
            ui.showLine();
            System.out.println("No trades available to generate a summary.");
            ui.showLine();
            return;
        }

        int totalTrades = tradeList.size();
        int winningTrades = 0;
        int losingTrades = 0;
        double totalWinR = 0;
        double totalLossR = 0;
        double totalR = 0;

        for (int i = 0; i < totalTrades; i++) {
            Trade trade = tradeList.getTrade(i);
            double rr = trade.getRiskRewardRatio();
            totalR += rr;

            if (rr > 0) {
                winningTrades++;
                totalWinR += rr;
            } else if  (rr < 0) {
                losingTrades++;
                totalLossR += Math.abs(rr);
            }
        }

        double winRate = ((double) winningTrades / totalTrades) * 100;
        double averageWin = winningTrades > 0 ? (totalWinR / winningTrades) : 0;
        double averageLoss = losingTrades > 0 ? (totalLossR / losingTrades) : 0;
        double expectedValue = totalR / totalTrades;

        String expectedValueSign = expectedValue > 0 ? "+" : "-";
        String totalRSign = totalR > 0 ? "+" : "-";

        ui.showLine();
        System.out.println("Overall Performance:\n");
        System.out.println("Total Trades: " + totalTrades);
        System.out.printf("Win Rate: %.0f%%\n", winRate);
        System.out.printf("Average Win: %.2fR\n", averageWin);
        System.out.printf("Average Loss: %.2fR\n", averageLoss);
        System.out.printf("Overall EV: %s%.2fR\n", expectedValueSign, expectedValue);
        System.out.printf("Total R: %s%.2fR\n", totalRSign, totalR);
        ui.showLine();
    }
}
