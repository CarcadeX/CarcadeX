public class PurchaseSerializer implements CommonSerializer<Purchase> {
    @Override
    public String serialize(Purchase purchase) {
        return purchase.idOnForum() +
                "\n" +
                purchase.command() +
                "\n" +
                String.join(",", purchase.allowedServers()) +
                "\n" +
                String.join(",", purchase.allowedWorlds()) +
                "\n" +
                purchase.icon().parseMaterial().name();
    }

    @Override
    public Purchase deserialize(String s) {
        String[] args = s.split("\n");
        return new Purchase(
                Integer.parseInt(args[0]),
                args[1],
                args[2].split(","),
                args[3].split(","),
                XMaterial.valueOf(args[4])
        );
    }
}