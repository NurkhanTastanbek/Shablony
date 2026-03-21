import java.util.*;

interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
    double calculateCost(String orderId);
}

class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        System.out.println("[Internal] Delivering order: " + orderId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return "[Internal] Status for " + orderId + ": In Transit";
    }

    @Override
    public double calculateCost(String orderId) {
        return 10.0;
    }
}


class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("[ServiceA] Shipping item ID: " + itemId);
    }

    public String trackShipment(int shipmentId) {
        return "[ServiceA] Shipment " + shipmentId + " is being processed";
    }

    public float getPrice(int id) {
        return 25.5f;
    }
}

class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("[ServiceB] Sending package: " + packageInfo);
    }

    public String checkPackageStatus(String trackingCode) {
        return "[ServiceB] Tracking code " + trackingCode + " status: Delivered";
    }
}

class ExternalLogisticsServiceC {
    public void dispatch(String destination, double weight) {
        System.out.println("[ServiceC] Dispatching to " + destination + " with weight " + weight + "kg");
    }

    public boolean isReceived(String ref) {
        return true;
    }

    public double quote(double w) {
        return w * 5.5;
    }
}


class LogisticsAdapterA implements IInternalDeliveryService {
    private ExternalLogisticsServiceA serviceA;

    public LogisticsAdapterA(ExternalLogisticsServiceA service) {
        this.serviceA = service;
    }

    @Override
    public void deliverOrder(String orderId) {
        try {
            int id = Integer.parseInt(orderId);
            serviceA.shipItem(id);
        } catch (NumberFormatException e) {
            System.out.println("[AdapterA Error] Invalid ID format for ServiceA: " + orderId);
        }
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        try {
            int id = Integer.parseInt(orderId);
            return serviceA.trackShipment(id);
        } catch (NumberFormatException e) {
            return "[AdapterA Error] Cannot track: Invalid ID";
        }
    }

    @Override
    public double calculateCost(String orderId) {
        return serviceA.getPrice(100); 
    }
}

class LogisticsAdapterB implements IInternalDeliveryService {
    private ExternalLogisticsServiceB serviceB;

    public LogisticsAdapterB(ExternalLogisticsServiceB service) {
        this.serviceB = service;
    }

    @Override
    public void deliverOrder(String orderId) {
        serviceB.sendPackage("Order_Ref_" + orderId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return serviceB.checkPackageStatus("B-TRACK-" + orderId);
    }

    @Override
    public double calculateCost(String orderId) {
        return 30.0; // Fixed rate for Service B
    }
}

class LogisticsAdapterC implements IInternalDeliveryService {
    private ExternalLogisticsServiceC serviceC;

    public LogisticsAdapterC(ExternalLogisticsServiceC service) {
        this.serviceC = service;
    }

    @Override
    public void deliverOrder(String orderId) {
        System.out.println("[Log] Logging dispatch for Service C...");
        serviceC.dispatch("Global Warehouse", 15.0);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        boolean status = serviceC.isReceived(orderId);
        return "[ServiceC] Received: " + status;
    }

    @Override
    public double calculateCost(String orderId) {
        return serviceC.quote(15.0);
    }
}


class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String type) {
        switch (type.toLowerCase()) {
            case "internal":
                return new InternalDeliveryService();
            case "service_a":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "service_b":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            case "service_c":
                return new LogisticsAdapterC(new ExternalLogisticsServiceC());
            default:
                throw new IllegalArgumentException("Unknown delivery service type: " + type);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        String[] serviceTypes = {"internal", "service_a", "service_b", "service_c"};
        String orderId = "12345";

        System.out.println("=== Logistics Monitoring System ===");

        for (String type : serviceTypes) {
            try {
                IInternalDeliveryService service = DeliveryServiceFactory.getDeliveryService(type);
                
                System.out.println("\n--- Using Service: " + type.toUpperCase() + " ---");
                service.deliverOrder(orderId);
                System.out.println("Status: " + service.getDeliveryStatus(orderId));
                System.out.println("Cost: $" + service.calculateCost(orderId));
                
            } catch (Exception e) {
                System.out.println("Error processing service " + type + ": " + e.getMessage());
            }
        }

        System.out.println("\n--- Error Handling Test (Invalid ID for Service A) ---");
        IInternalDeliveryService adapterA = DeliveryServiceFactory.getDeliveryService("service_a");
        adapterA.deliverOrder("INVALID_ID");
    }
}
