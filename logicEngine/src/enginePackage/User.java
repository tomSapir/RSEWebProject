package enginePackage;

public class User
{
    /******************************************************************************/
    public enum Type {ADMIN, TRADER}
    /******************************************************************************/
    private String name;
    private UserHoldings holdings;
    private Type type;
    private int loadedMoney;
    /******************************************************************************/
    public User(String name, Type type)
    {
        if (type == Type.TRADER)
            holdings = new UserHoldings();
        this.name = name;
        this.type = type;
    }
    /******************************************************************************/
    public String getName() { return name; }
    public UserHoldings getHoldings() { return holdings; }
    public Type getType() { return type; }
    public int getLoadedMoney() { return loadedMoney; }
    /******************************************************************************/
    public void updateHoldings(int stockAmount, Command.Way way, Stock stock)
    {
        if (way == Command.Way.BUY)
            loadedMoney -= (stock.getCurrValue() * stockAmount);
        else
            loadedMoney += (stock.getCurrValue() * stockAmount);

        holdings.updateHoldings(stock.getSymbol(), stockAmount, stock.getCurrValue(), way);
    }
    /******************************************************************************/
    public void addMoney(int money) { this.loadedMoney += money; }
    /******************************************************************************/
}
