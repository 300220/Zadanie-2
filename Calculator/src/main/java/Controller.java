import static javafx.scene.input.KeyCode.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 
 * Klasa implementujaca controller
 *
 */

public class Controller {
  private IModel model;
  
  /**
   * Konstruktor jednoargumentowy
   * @param model
   */
  public Controller(IModel model) {
	    this.model = model;
  }

  @FXML
  private Label output;

  private double number1 = 0;
  private String operator = "";
  private boolean start = true;

  /**
   *  Zbiory przyjmowanych przyciskow
   */
  KeyCode[] numpadKey = {NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9, DECIMAL,
      DIGIT0, DIGIT1, DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9, PERIOD};
  KeyCode[] operatorKey = {DIVIDE, MULTIPLY, SUBTRACT, ADD, MINUS, SLASH};

  /**
   * obsluga interfejsu klawiatury numerycznej
   * @param event - zdarzenie
   */
  @FXML
  public void processNumpad(ActionEvent event) {
    String value = ((Button) event.getSource()).getText();
    addText(value);
  }

  /**
   * obsluga interfejsu operatorow
   * @param event - zdarzenie
   */
  @FXML
  public void processOperator(ActionEvent event) {
    String value = ((Button) event.getSource()).getText();
    addOperator(value);
  }

  /**
   * ustawianie textu w widoku aplikacji
   * @param value
   */
  public void addText(String value) {
    if (start) {
      output.setText("");
      start = false;
    }

    // Aby nie przekorczyc dlugosci okna wyswietlania
    int MAX_OUTPUT_LENGTH = 19;
    if (!(output.getText().length() == MAX_OUTPUT_LENGTH)) {
      // Jezeli pierwszym znakiem jest kropka (.) dodaj 0 przed znakiem
      if (output.getText().length() == 0 && value.equals("."))
        output.setText("0");
      // Jezeli w lancuchu znakow jest kropka nie pozwol na dodanie kolejnej
      if (value.equals(".")) {
        int dot = 1;
        for (int i = 0; i < output.getText().length(); ++i) {
          if (output.getText().charAt(i) == '.') dot += 1;
          if (dot == 2) return;
        }
      }
      // Jezeli pierwsza liczba to zero nie dodawaj kolejnego zera
      if (output.getText().length() == 1 && output.getText().charAt(0) == '0' && value.equals("0")) return;
      // Jezeli pierwsza liczba to zero a drugi znak nie jest kropka skasuj zero
      if (output.getText().length() == 1 && output.getText().charAt(0) == '0' && !value.equals(".")) {
        output.setText(output.getText().substring(0, 0));
      }
      output.setText(output.getText() + value);
    }
  }

  /**
   * ustawianie operatora oraz wynikow dzialan matematycznych
   * @param value operator
   */
  public void addOperator(String value) {
    // Kasowanie wyniku/wyswietlacza
    if (value.equals("C")) {
      cleanOutput();
    }
    // Jezeli brak liczb, nic nie rob przy kliknieciu operoatorow
    if (output.getText().length() == 0) {
      return;
    }

    // Jezeli operatorem nie jest znak =
    if (!"=".equals(value)) {
      // Funkcje operujace na jednej liczbie
      if (value.equals("sqrt") || value.equals("+/-") || value.equals("%")) {
        try {
          output.setText(String.valueOf(model.calculate(Double.parseDouble(output.getText()), value)));
        } catch (CalculationError e) {
          e.printStackTrace();
          output.setText("ERR");
        }
      } else {
        operator = value;
        // Jezeli jest juz pierwsza liczba i podana druga, przyciskajac operator matematyczny - oblicza i podaje wynik, przypisuje go do liczby1
        if (number1 > 0) {
          output.setText(String.valueOf(model.calculate(number1, Double.parseDouble(output.getText()), operator)));
          number1 = Double.parseDouble(output.getText());
          start = true;
        }
        // jezeli to pierwsza liczba, przypisuje ja do number1
        else {
          number1 = Double.parseDouble(output.getText());
        }
        start = true;
      }
    }
    // Jezeli operatorem jest znak =
    else {
      if (operator.isEmpty()) return;
      prepareOutput(); //wynik

    }
  }

  /**
   * obsluga klawiatury
   * @param event
   */
  @FXML
  public void setOnKeyPressed(KeyEvent event) {
    String value = event.getText();

    // Jezeli enter albo przycisk =, daj wynik
    if (event.getCode() == ENTER || event.getCode() == EQUALS) {
      if (operator.isEmpty()) return;
      prepareOutput(); //wynik
      return;
    }
    // Jezeli przycisk DEL kasuj
    if (event.getCode() == DELETE) {
      cleanOutput();
      return;
    }
    // dodaj wartosc jezeli przycisk sie zgadza z tabela numeryczna
    for (KeyCode keyCode : numpadKey) {
      if (event.getCode() == keyCode) {
        if (event.getCode() == DECIMAL) value = ".";
        addText(value);
        break;
      }
    }
    // dodaj opertator jezeli przysick zgadza sie z tabela operatorow
    for (KeyCode keyCode : operatorKey) {
      if (keyCode == event.getCode()) {
        addOperator(value);
        break;
      }
    }
  }
/**
 * Czyszczenie wyswietlanego tekstu
 */
  public void cleanOutput() {
    output.setText("");
    operator = "";
    number1 = 0;
    start = true;
  }
/**
 *  Pobiera pierwsza liczbe i przesyla druga wraz z operatorem
 */
  public void prepareOutput() {
    // String do sprawdzenia ostatnich cyfer
    System.out.println(operator);
    System.out.println(number1);
    System.out.println(output.getText());
    String checkString = String.valueOf(model.calculate(number1, Double.parseDouble(output.getText()), operator));

    // Jezeli na koncu wyniku jest (.0) skasuj
    if (checkString.charAt(checkString.length() - 1) == '0' && checkString.charAt(checkString.length() - 2) == '.') {
      output.setText(checkString.substring(0, checkString.length() - 2));
    } else {
      output.setText(checkString);
    }
    number1 = 0;
    operator = "";
    start = true;
  }
}
