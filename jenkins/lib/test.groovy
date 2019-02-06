
class Test_ {
    private String name

    Test_(String name) {
        this.name = name
    }

    String greet() { "Hello, ${name}" }
}

Test_ create(name) { new Test_(name) }

return this
