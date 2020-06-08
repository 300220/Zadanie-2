
public interface IModel {
	double calculate(double number1, double number2, String operator);
	double calculate(double number1, String operator) throws CalculationError;
}
