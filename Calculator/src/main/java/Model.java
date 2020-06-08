import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jdk.jshell.*;
/**
 * 
 * Klasa implementujaca model 
 *
 */
public class Model implements IModel{
/**
 * Obliczanie dla operacji dwuargumentowych
 * @param number1
 * @param number2
 * @param operator
 * @return wynik
 */
  public double calculate(double number1, double number2, String operator) {
    JShell jShell = JShell.create();
    String expression = number1 + operator + number2;
    var events = jShell.eval(expression);
    for (SnippetEvent e : events) {
      if (e.value() != null) {
        return Double.parseDouble(e.value());
      }
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
        return number1 / 100;
      case "+/-":
        return -number1;
      case "sqrt":
        if (number1 < 0) {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Nie mozna wyciagac pierwiastka kwadratowego z liczb ujemnych", ButtonType.CLOSE);
          alert.showAndWait();
          throw (new CalculationError("Nie mozna wyciagac pierwiastka kwadratowego z liczb ujemnych"));
        }
        return Math.sqrt(number1);
    }

    Alert alert = new Alert(Alert.AlertType.ERROR, "Nieznany operator - " + operator, ButtonType.CLOSE);
    alert.showAndWait();
    System.out.println("Nieznany operator - " + operator);
    return 0;
  }

}