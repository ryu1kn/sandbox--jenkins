
class Test {
    private String name

    Test(String name) {
        this.name = name
    }

    String greet() { "Hello, ${name}" }
}
