package sjsonnet;

public class Example {
    public void example(){
        sjsonnet.SjsonnetMain.main0(
            new String[]{"foo.jsonnet"},
            new sjsonnet.Parser(),
            System.in,
            System.out,
            System.err,
            ammonite.ops.Path.apply(
                System.getProperty("user.dir"),  // working directory
                ammonite.ops.PathConvertible.StringConvertible$.MODULE$
            )
        );
    }
}
