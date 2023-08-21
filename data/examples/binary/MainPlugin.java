class MainPlugin {
    @Data
    class User implements Serializable {
        private static final long serialVersionUID = 6529685098267757690L;
        private final String name;
        private int age;
        private List<String> children;
    }

    @Override
    public void onEnable() {
        MutableRepo<String, User> repo = Repo.<String, User> builder()
                .folder(File("data")
                        .binary()
                        .build();
    }
}