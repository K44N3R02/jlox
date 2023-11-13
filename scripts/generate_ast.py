import io


def writeln(file: io.TextIOWrapper, line: str = ""):
    file.write(line)
    file.write("\n")

def define_ast(out_dir: str, base_name: str, types: list[str]):
    path = f"{out_dir}/{base_name}.java"

    with open(path, "w") as f:
        writeln(f, "package com.jlox;")
        writeln(f)
        writeln(f, "import java.util.List;")
        writeln(f)
        writeln(f, f"abstract class {base_name} "+"{")

        define_visitor(f, base_name, types)

        writeln(f)
        writeln(f, f"    // Nested {base_name} classes here...")

        for t in types:
            cls_name = t.split(":")[0].strip()
            fields = t.split(":")[1].strip()
            define_type(f, base_name, cls_name, fields)

        writeln(f, "    abstract <R> R accept(Visitor<R> visitor);")
        writeln(f, "}")


def define_type(f: io.TextIOWrapper, base_name: str, cls_name: str, fields: str):
    writeln(f)
    writeln(f, f"    static class {cls_name} extends {base_name} "+"{")
    # ctor
    writeln(f, f"        {cls_name}({fields}) "+"{")
    for field in fields.split(", "):
        name = field.split(" ")[1]
        writeln(f, f"            this.{name} = {name};")
    writeln(f, "        }")

    # visitor pattern
    writeln(f)
    writeln(f, "        @Override")
    writeln(f, "        <R> R accept(Visitor<R> visitor) {")
    writeln(f, f"            return visitor.visit{cls_name}{base_name}(this);")
    writeln(f, "        }")

    # fields
    writeln(f)
    for field in fields.split(", "):
        writeln(f, f"        final {field};")

    writeln(f, "    }")

def define_visitor(f: io.TextIOWrapper, base_name: str, types: list[str]):
        writeln(f)
        writeln(f, "    interface Visitor<R> {")
        
        for t in types:
            t_name = t.split(":")[0].strip()
            writeln(f, f"        R visit{t_name}{base_name}({t_name} {base_name.lower()});")
        
        writeln(f, "    }")
    


if __name__ == "__main__":
    define_ast("../demo/src/main/java/com/jlox", "Expr", [
        "Binary   : Expr left, Token operator, Expr right",
        "Grouping : Expr expression",
        "Literal  : Object value",
        "Unary    : Token operator, Expr right"
    ])