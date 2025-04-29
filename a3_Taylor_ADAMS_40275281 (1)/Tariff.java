//------------------------------------------------------------
// Assignment 3
// Written by: Taylor Adams 40275281
//---------------------------------------------------------

// This file contains the Tariff class, which represents a tariff with destination country, origin country, product category, and minimum tariff.


public class Tariff {
    private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private double minimumTariff;

    // default and parameterized constructor
    public Tariff() {
        this.destinationCountry = "";
        this.originCountry = "";
        this.productCategory = "";
        this.minimumTariff = 0.0;
    }

    public Tariff(String destinationCountry, String originCountry, String productCategory) {
        // the \\s+ regex matches one or more whitespace characters
        this.destinationCountry = destinationCountry.replaceAll("\\s+", "_");
        this.originCountry = originCountry.replaceAll("\\s+", "_");
        this.productCategory = productCategory.replaceAll("\\s+", "_");
        this.minimumTariff = 0.0;
    }

    public Tariff(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public Tariff(Tariff other) {
        this.destinationCountry = other.destinationCountry;
        this.originCountry = other.originCountry;
        this.productCategory = other.productCategory;
        this.minimumTariff = other.minimumTariff;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public double getMinimumTariff() {
        return minimumTariff;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tariff other = (Tariff) obj;
        return destinationCountry.equals(other.destinationCountry) &&
               originCountry.equals(other.originCountry) &&
               productCategory.equals(other.productCategory) &&
               Double.compare(minimumTariff, other.minimumTariff) == 0;
    }

    public class Show {
        // Parameterized constructor
        public Show(String destinationCountry, String originCountry, String productCategory, double minimumTariff) {
            Tariff.this.destinationCountry = destinationCountry.replaceAll("\\s+", "_");
            Tariff.this.originCountry = originCountry.replaceAll("\\s+", "_");
            Tariff.this.productCategory = productCategory.replaceAll("\\s+", "_");
            Tariff.this.minimumTariff = minimumTariff;
        }

        // Copy constructor (takes a Tariff object)
        public Show(Tariff tariff) {
            Tariff.this.destinationCountry = tariff.destinationCountry;
            Tariff.this.originCountry = tariff.originCountry;
            Tariff.this.productCategory = tariff.productCategory;
            Tariff.this.minimumTariff = tariff.minimumTariff;
        }

        // clone() method
        public Show clone() {
            return new Show(Tariff.this.destinationCountry, Tariff.this.originCountry, Tariff.this.productCategory, Tariff.this.minimumTariff);
        }

        // toString() method
        @Override
        public String toString() {
            return String.format(
                "Destination Country: %s\nOrigin Country: %s\nProduct Category: %s\nMinimum Tariff: %.2f",
                Tariff.this.destinationCountry, Tariff.this.originCountry, Tariff.this.productCategory, Tariff.this.minimumTariff
            );
        }

        // equals() method
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Show other = (Show) obj;
            return Tariff.this.destinationCountry.equals(other.getDestinationCountry()) &&
                   Tariff.this.originCountry.equals(other.getOriginCountry()) &&
                   Tariff.this.productCategory.equals(other.getProductCategory()) &&
                   Double.compare(Tariff.this.minimumTariff, other.getMinimumTariff()) == 0;
        }

        // Accessor methods
        public String getDestinationCountry() {
            return Tariff.this.destinationCountry;
        }

        public String getOriginCountry() {
            return Tariff.this.originCountry;
        }

        public String getProductCategory() {
            return Tariff.this.productCategory;
        }

        public double getMinimumTariff() {
            return Tariff.this.minimumTariff;
        }

        // Mutator methods
        public void setDestinationCountry(String destinationCountry) {
            Tariff.this.destinationCountry = destinationCountry.replaceAll("\\s+", "_");
        }

        public void setOriginCountry(String originCountry) {
            Tariff.this.originCountry = originCountry.replaceAll("\\s+", "_");
        }

        public void setProductCategory(String productCategory) {
            Tariff.this.productCategory = productCategory.replaceAll("\\s+", "_");
        }

        public void setMinimumTariff(double minimumTariff) {
            Tariff.this.minimumTariff = minimumTariff;
        }
    }
}
