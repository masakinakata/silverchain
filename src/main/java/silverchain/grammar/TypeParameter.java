package silverchain.grammar;

public final class TypeParameter extends ASTNode1<String> {

  public TypeParameter(String name) {
    super(name);
  }

  public String name() {
    return object();
  }

  @Override
  public String toString() {
    return name();
  }
}