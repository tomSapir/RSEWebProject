package enginePackage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import generated.*;

public class Stocks
{
    /******************************************************************************/
    private Map<String, Stock> stocks;
    /******************************************************************************/
    public Stocks() { stocks = new HashMap<>(); }
    /******************************************************************************/
    public Collection<Stock> getStocks() { return stocks.values(); }
    /******************************************************************************/
    public void loadData(RseStocks xmlStocks)
    {
        xmlStocks.getRseStock().forEach((s)->stocks.put(s.getRseSymbol(), new Stock(s)));
    }
    /******************************************************************************/
    public void clearData() { stocks.clear(); }
    /******************************************************************************/
    // This method will find a stock by its name (SYMBOL).
    // if there is no stock like this the method will return null!
    public Stock findStock(String symbol) { return stocks.get(symbol); }
    /******************************************************************************/
}
