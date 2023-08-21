class MainPlugin {
    @Override
    public void onEnable() {
        MutableRepo<String, Purchase> repo = Repo.<String, User> builder(File("data"))
                        .serializer(new PurchaseSerializer())
                        .build();
    }
}