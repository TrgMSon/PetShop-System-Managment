package Invoice;

public class Invoice {
    private String idInvoice, idCustomer, date;
    
    public Invoice() {

    }

    public Invoice(String idInvoice, String idCustomer, String date) {
        this.idInvoice = idInvoice;
        this.idCustomer = idCustomer;
        this.date = date;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}