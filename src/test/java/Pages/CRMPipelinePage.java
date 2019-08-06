package Pages;

import Utilities.BriteERPUtils;
import Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CRMPipelinePage {

    public CRMPipelinePage() {
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(css = "button[data-original-title=Pivot]")
    public WebElement pivotButton;

    @FindBy(css = "button[data-original-title=List]")
    public WebElement listButton;

    @FindBy(xpath = "//tr//td[@class='o_pivot_header_cell_opened']")
    public WebElement totalButtonOpened;

    @FindBy(xpath = "//tr//td[@class='o_pivot_header_cell_closed']")
    public WebElement getTotalButtonClosed;

    @FindBy(xpath = "//a[.='Opportunity']")
    public WebElement opportunity;

    @FindBy(xpath = "(//tr//td[@class='o_pivot_cell_value text-right'])[5]")
    public WebElement tablePivotBookSale;

    @FindBy(xpath = "(//tr//td[@class='o_data_cell o_list_number'])[1]")
    public WebElement tableListBookSale;

    @FindBy(xpath = "(//tr//td[@class='o_pivot_cell_value text-right'])[1]")
    public WebElement totalPrice;

    @FindBy(xpath = "(//tr//td[@class='o_pivot_cell_value text-right'])[3]")
    public WebElement price1;

    @FindBy(xpath = "(//tr//td[@class='o_pivot_cell_value text-right'])[5]")
    public WebElement price2;

    @FindBy(xpath = "(//tr//td[@class='o_pivot_cell_value text-right'])[7]")
    public WebElement price3;


    @FindBy(xpath = "//table/tbody/tr[3]/td[1]")
    public WebElement pivotLine2;

    @FindBy(xpath = "//table/tbody/tr[3]/td[2]")
    public WebElement pivotLine2Price;

    //table/tbody/tr/td[3]

    @FindBy(xpath = "//table/tbody/tr/td[3]")
    public List<WebElement> listColumn3;

    public double listColumnPrice(){
        int index = 0;
        for(int i=0; i < listColumn3.size(); i++){
            if (listColumn3.get(i).getText().contains(pivotLine2.getText())){
                index = i+1;
            }
        }
        String listColumn3Str = Driver.get().findElement(By.xpath("//table/tbody/tr["+index+"]/td[9]")).getText();
        double listColumn3Price = Double.parseDouble(listColumn3Str.replace(",", ""));

        return listColumn3Price;
    }



    //**********
    public double opportunityText() {
        int index = 0;

        List<WebElement> opportunity = Driver.get().findElements(By.xpath("//table/tbody/tr/td"));
        List<String> OpportunityStr = new ArrayList<>();

        for (WebElement str:opportunity) {
            OpportunityStr.add(str.getText());
        }
        System.out.println("OpportunityStr = " + OpportunityStr.toString());


        System.out.println("OpportunityStr size=" + OpportunityStr.size());

        for (WebElement s : opportunity) {
            System.out.println(s.getText());
        }


        for (int i = 0; i < opportunity.size(); i++) {
            if (Driver.get().findElements(By.xpath("//table/tbody/tr["+i+"]/td[1]")).get(i).getText().contains("Book Sale")) {
                index = i;
                break;

            }
            BriteERPUtils.waitForUIOverlay();
            Driver.get().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            String revenue = Driver.get().findElement(By.xpath("//table/tbody/tr[index]/td[2]")).getText().replace(",", "");
            double revenueDouble = Double.parseDouble(revenue);
            return revenueDouble;
        }

       return 0;
    }

//************************ AC-2*******************************************
// Acceptance criteria 2: Sumup the price on the relevant line of pivot version list

    public double sumOfRevenue() {
        List<WebElement> totalRevenue = new ArrayList<>();
        totalRevenue = Driver.get().findElements(By.xpath("//tbody/tr/td[2]"));             //tbody/tr[1]/td[2]


        List<String> totalRevStr = new ArrayList<>();
        for (WebElement w : totalRevenue) {
            totalRevStr.add(w.getText());
        }
        List<Double> totalREvDouble = new ArrayList<>();

        for (String s : totalRevStr) {
            totalREvDouble.add(Double.parseDouble(s.replace(",", "")));
        }

        double sumOfActDouble = 0;
        int sumInt = 0;

        for (Double td : totalREvDouble) {
            sumOfActDouble += td;
        }
        return sumOfActDouble;

    }

}
