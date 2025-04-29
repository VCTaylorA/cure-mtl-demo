//------------------------------------------------------------
// Assignment 3
// Written by: Taylor Adams 40275281
//---------------------------------------------------------

// This file defines a method for evaluating trade tariffs, which will be used in the TariffPolicy class.
public interface TariffPolicy {
    String EvaluateTrade(double proposedTariff, double minimumTariff);
}
