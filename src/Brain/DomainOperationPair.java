package Brain;

import Things.Domain;
import Things.Operation;

public class DomainOperationPair {
    private final Domain domain;
    private final Operation operation;


    private double confidence;

    public DomainOperationPair(Domain domain, Operation operation, double confidence) {
        this.domain = domain;
        this.operation = operation;
        this.confidence = confidence;
    }

    public Domain getDomain() {
        return domain;
    }

    public Operation getOperation() {
        return operation;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "DOP{" +
                "domain=" + domain.getId() +
                ", operation=" + operation.getId() +
                ", confidence=" + confidence +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainOperationPair that = (DomainOperationPair) o;

        if (Double.compare(that.confidence, confidence) != 0) return false;
        return domain != null ? domain.equals(that.domain) : that.domain == null && (operation != null ? operation.equals(that.operation) : that.operation == null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        temp = Double.doubleToLongBits(confidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
