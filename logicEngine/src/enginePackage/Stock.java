package enginePackage;

import DTOs.TradeDTO;
import generated.RseStock;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Stock
{
    /******************************************************************************/
    private String companyName;
    private String symbol;
    private int currValue;
    private int transactionsCycle = 0;
    private List<Trade> transactionsHistory;
    private Commands buyCommands;   // waiting list for buy
    private Commands sellCommands;  // waiting list for sell
    /******************************************************************************/
    public Stock(String companyName, String symbol, int currValue)
    {
        this.companyName = companyName;
        this.symbol = symbol;
        this.currValue = currValue;
        transactionsHistory = new ArrayList();
        buyCommands = new Commands(false);  // false -> waiting list for buy
        sellCommands = new Commands(true);  // true -> waiting list for sell
    }
    /******************************************************************************/
    public Stock(RseStock src)
    {
        this.companyName = src.getRseCompanyName();
        this.symbol = src.getRseSymbol().toUpperCase(Locale.ROOT);
        this.currValue = src.getRsePrice();
        transactionsHistory = new ArrayList();
        buyCommands = new Commands(false);  // false -> waiting list for buy
        sellCommands = new Commands(true);  // true -> waiting list for sell
    }
    /******************************************************************************/
    // This method takes the last transaction and update the current value of the stock.
    // In addition the method add
    public void setCurrValue()
    {
        if(transactionsHistory.size() != 0)
        {
            Trade tmpTrade = transactionsHistory.get(transactionsHistory.size() - 1);
            this.currValue = tmpTrade.getPrice();
            this.transactionsCycle += tmpTrade.getTotalSum();
        }
    }
    /******************************************************************************/
    public String getCompanyName() { return companyName; }
    public String getSymbol() { return symbol; }
    public int getCurrValue() { return currValue; }
    public int getTransactionsCycle() { return transactionsCycle; }
    public List<Trade> getTransactionsHistory() { return transactionsHistory; }
    public Map<Integer, List<Command>> getBuyCommands() { return buyCommands.getCommands(); }
    public Map<Integer, List<Command>> getBSellCommands() { return buyCommands.getCommands(); }
    /******************************************************************************/
    public void addTransaction(Trade trade)
    {
        transactionsHistory.add(trade);
        transactionsCycle += trade.getTotalSum();
    }
    /******************************************************************************/
    // This method submit new command to the engine
    public TradeDTO addCommand(Command cmd)
    {
        TradeDTO tradeDescription = new TradeDTO(cmd.getAmount());

        if(cmd.getWay() == Command.Way.BUY) // buy command
            sellCommands.searchMatch(cmd, transactionsHistory, true, tradeDescription);
        else    // sell command
            buyCommands.searchMatch(cmd, transactionsHistory, false, tradeDescription);

        // this.setCurrValue();

        if(cmd.getStockAmount() != 0)   // in case of some of the offer is relevent
        {
            if(cmd.getWay() == Command.Way.BUY)
                buyCommands.addCommand(cmd, this.currValue);
            else
                sellCommands.addCommand(cmd, this.currValue);
        }

        return tradeDescription;
    }
    /******************************************************************************/
}