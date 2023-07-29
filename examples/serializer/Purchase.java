public record Purchase(int idOnForum,
        String command,
        String[] allowedServers,
        String[] allowedWorlds,
        XMaterial icon) {}