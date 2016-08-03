package Brain;

import Things.Domain;
import Things.Operation;

public class DomainOperationPair {
    private final Domain domain;
    private final Operation operation;
    private final double domainConfidence;
    private final double operationConfidence;

    public DomainOperationPair(Domain domain, double domainConfidence, Operation operation, double operationConfidence) {
        this.domain = domain;
        this.operation = operation;
        this.operationConfidence = operationConfidence;
        this.domainConfidence = domainConfidence;
    }

    public Domain getDomain() {
        return domain;
    }

    public Operation getOperation() {
        return operation;
    }


    @Override
    public String toString() {
        return "DOP{" +
                "domain=" + domain.getId() +
                ", operation=" + operation.getId() +
                ", domainConfidence=" + domainConfidence +
                ", operationConfidence=" + operationConfidence +
                '}';
    }

    public DomainOperationPair(Domain domain, Operation operation, double domainConfidence, double operationConfidence) {
        this.domain = domain;
        this.operation = operation;
        this.domainConfidence = domainConfidence;
        this.operationConfidence = operationConfidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainOperationPair that = (DomainOperationPair) o;

        return Double.compare(that.domainConfidence, domainConfidence) == 0 && Double.compare(that.operationConfidence, operationConfidence) == 0 && (domain != null ? domain.equals(that.domain) : that.domain == null && (operation != null ? operation.equals(that.operation) : that.operation == null));

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        temp = Double.doubleToLongBits(domainConfidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(operationConfidence);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    double getDomainConfidence() {
        return domainConfidence;
    }

    double getOperationConfidence() {
        return operationConfidence;
    }

}
