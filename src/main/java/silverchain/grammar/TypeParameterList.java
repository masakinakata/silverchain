package silverchain.grammar;

public final class TypeParameterList extends ASTNodeN<TypeParameter, TypeParameterList> {

  public TypeParameterList(Range range, TypeParameter head, TypeParameterList tail) {
    super(range, head, tail);
  }

  @Override
  String separator() {
    return ",";
  }
}
