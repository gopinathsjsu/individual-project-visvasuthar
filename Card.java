public class Card
{
    private String cardNumber;
    
    public Card(Builder build){
        this.cardNumber = build.cardNumber;
    }
    
    public String getCardNumber(){
        return this.cardNumber;
    }
    
    @Override
    public String toString(){
        return ""+this.cardNumber+"";
    }
    
    public static class Builder{
        public String cardNumber;
        
        public Builder(String cardNumber){
            this.cardNumber = cardNumber;
        }
        
        public Card buildCard(){
            return new Card(this);
        }
    }
}
