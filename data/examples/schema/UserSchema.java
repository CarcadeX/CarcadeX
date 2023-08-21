@RequiredArgsConstructor
class UserSchema implements SchemaStrategy<Integer, User> {
    private final MySQL mysql;

    @Override
    public void init() {
        mysql.query("CREATE TABLE IF NOT EXISTS table (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(25) NOT NULL," +
                "age INT NOT NULL" +
                ");");
    }

    @Override
    public void close() {
        mySQL.closeConnection();
    }

    @Override
    public Collection<User> all() {
        List<User> result = new ArrayList<>();
        mysql.query("SELECT * from table;",results->{
            if(results != null)
                while(results.next()){
                    result.add(new User(
                            results.getInt("id"),
                            results.getString("name"),
                            results.getInt("age")
                    ));
                }
        });
        return result;
    }

    @Override
    public User get(Integer key) {
        mysql.query("SELECT * from table WHERE id = "+key+";",results->{
            if(results != null)
                if(results.next()){
                    return new User(
                            results.getInt("id"),
                            results.getString("name"),
                            results.getInt("age")
                    );
                }
        });
        throw new RuntimeException("not found");
    }

    @Override
    public void insert(Integer key, User value) {
        mysql.update("INSERT INTO `table` (`id`, `name`, `age`) VALUES ('"+key+"','"+
                value.getName()
                +"',"+
                value.getAge()
                +");");

    }

    @Override
    public void remove(Integer key) {
        mysql.query("DELETE FROM table WHERE id =" + key + ";");
    }
}