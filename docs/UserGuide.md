# User Guide

## 1. Introduction

**TradeLog** provides a CLI-based, systematic way to log trades and test systems that is faster and more efficient than logging trades on Google Sheets. It has the ability to automatically calculate trade details such as Risk:Reward ratio and the Expected Value (EV) of the system. By eliminating the friction of manual spreadsheet entry, TradeLog helps traders maintain a disciplined journal and identify their mathematical edge with precision.

## 2. Quick Start

1. Ensure that you have **Java 17** or above installed on your computer.
2. Download the latest version of `TradeLog.jar` from [here](https://github.com/AY2526S2-CS2113-T11-2/tp/releases).
3. Open your terminal, navigate to the folder containing the file, and run:
   `java -jar TradeLog.jar`

---

## 3. Features

### [Version 1.0] - Core Backtesting Suite
*These features form the baseline workflow that the current release supports.*

* **Adding a Trade: `add`** - Log new trades with ticker, date, direction, entry/exit/stop prices, outcome, and strategy.
* **Editing a Trade: `edit`** - Update one or more fields of an existing record using the trade index.
* **Deleting a Trade: `delete`** - Remove a trade by index to keep the log accurate.
* **Listing Trades: `list`** - Display every logged trade with numbering for easy reference.
* **Filtering Trades: `filter`** - Narrow the list by ticker, strategy, or date (supports partial matches with `-p`).
* **Performance Summary: `summary`** - Calculate win rate, average win/loss, EV, and total R across the current log.
* **Persistent Storage** - The application saves data to the default `trades.txt` file after every mutation.

### [Version 2.0] - Strategy & Analytics Enhancements
*This release adds more powerful reporting and strategy-friendly aliases.*

* **Strategy Shortcuts** - Enter common strategies via shorthand codes such as `BB`, `PB`, or `MTR` and TradeLog expands them to the canonical names before storing and filtering trades (see Section 6).
* **Strategy Comparison: `compare`** - View per-strategy metrics (win rate, average win/loss, and EV) in one report to identify the strongest systems (see Section 7).

### [Version 3.0] - Advanced Analytics & Export (Planned)
*The following ideas remain on the roadmap for future releases.*

* **Psychological Tagging** - Log emotional states to identify behavioral patterns.
* **Max Drawdown** - Automatic calculation of worst-case capital decline.
* **CSV Export** - Convert data to CSV for use in external tools like Excel.
* **Reflective Journaling** - Attach reflections and screenshots to each trade record.
* **Pre-trade Checklist** - Enforce pre-entry discipline before saving trades.
* **Bulk Import** - Import historical trades for large-scale system testing.

---

## 4. FAQ

**Q: How do I transfer my data to another computer?**
**A:** TradeLog saves your trades when you exit the application. Simply copy the `data/` folder and place it in the same directory as the `TradeLog.jar` file on your new computer.

**Q: What happens if I enter an invalid date or negative price?**
**A:** TradeLog will display an error message and will not save the invalid entry.

---

## 5. Command Summary

| Action           | Format                                                                                                    |
|:-----------------|:----------------------------------------------------------------------------------------------------------|
| **Add Trade**    | `add t/TICKER d/DATE dir/DIRECTION e/ENTRY x/EXIT s/STOP o/OUTCOME strat/STRATEGY`                        |
| **Edit Trade**   | `edit INDEX [t/TICKER] [d/DATE] [dir/DIRECTION] [e/ENTRY] [x/EXIT] [s/STOP] [o/OUTCOME] [strat/STRATEGY]` |
| **Delete Trade** | `delete INDEX`                                                                                            |
| **List Trades**  | `list`                                                                                                    |
| **Filter**       | `filter [-p] [t/TICKER] [strat/STRATEGY] [d/DATE]`                                                        |
| **Compare**      | `compare`                                                                                                 |
| **Summary**      | `summary`                                                                                                 |
| **Exit**         | `exit`                                                                                                    |

Accepted strategy shortcuts: `BB`, `TBF`, `PB`, `MTR`, `HOD`, `LOD`, `MR`, `TR`, `DB`, `DT`

## 6. Strategy Shortcuts

TradeLog expands common strategy abbreviations automatically before saving or filtering trades.

| Shortcut | Strategy |
|:---------|:---------|
| `BB`     | `Breakout` |
| `TBF`    | `Trend Bar Failure` |
| `PB`     | `Pullback` |
| `MTR`    | `Major Trend Reversal` |
| `HOD`    | `High of Day` |
| `LOD`    | `Low of Day` |
| `MR`     | `Mean Reversion` |
| `TR`     | `Trading Range` |
| `DB`     | `Double Bottom` |
| `DT`     | `Double Top` |

Example:

`add t/AAPL d/2026-02-18 dir/long e/180 x/190 s/170 o/win strat/BB`

TradeLog stores and displays that strategy as `Breakout`.

## 7. Strategy Comparison

Use `compare` to view grouped performance metrics by strategy.

Example:

`compare`

Expected output format:

```text
Strategy Comparison:

Breakout:
Trades: 15
Win Rate: 60%
Average Win: 2.02R
Average Loss: 0.95R
EV: +0.832R

Pullback:
Trades: 20
Win Rate: 50%
Average Win: 1.50R
Average Loss: 1.00R
EV: +0.250R
```
