import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

/**
 * 
 * Klasa implementujaca model 
 *
 */
public class ModelWithExternalService implements IModel{
  private CalculatorClient calculatorClient = new CalculatorClient();
/**
 * Obliczanie dla operacji dwuargumentowych
 * @param number1
 * @param number2
 * @param operator
 * @return wynik
 */
  public double calculate(double number1, double number2, String operator) {

    //return calculatorClient.add(number1,number2);
    switch (operator)
    {
      case "+":
        return calculatorClient.add(number1,number2);
      case "-":
        return calculatorClient.sub(number1,number2);
      case "*":
        return calculatorClient.mul(number1,number2);
      case "/":
        return calculatorClient.div(number1,number2);
    }

    System.out.println("Nieznany operator - " + operator);
    Alert alert = new Alert(Alert.AlertType.ERROR, "Nieznany operator - " + operator, ButtonType.CLOSE);
    alert.showAndWait();
    return 0;
  }
/**
 * Obliczanie dla operacji jednoargumentowych
 * @param number1
 * @param operator
 * @return wynik
 * @throws CalculationError
 */
  public double calculate(double number1, String operator) throws CalculationError {
    switch (operator) {
      case "%":
        return calculatorClient.div(number1, 100);
      case "+/-":
        return calculatorClient.neg(number1);
      case "sqrt":
        if (number1 < 0) {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Nie mozna wyciagac pierwiastka kwadratowego z liczb ujemnych", ButtonType.CLOSE);
          alert.showAndWait();
          throw (new CalculationError("Nie mozna wyciagac pierwiastka kwadratowego z liczb ujemnych"));
        }
        return calculatorClient.sqrt(number1);
    }

    Alert alert = new Alert(Alert.AlertType.ERROR, "Nieznany operator - " + operator, ButtonType.CLOSE);
    alert.showAndWait();
    System.out.println("Nieznany operator - " + operator);
    return 0;
  }

}