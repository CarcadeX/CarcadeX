class MainPlugin {
    @Override
    public void onEnable() {
        MySQL mysql = ...;
        Repo.<Integer, User> builder().
                .schema(new UserSchema(mysql))
                .build();
    }
}